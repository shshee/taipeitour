package com.tangerine.taipeitour.viewmodel

import androidx.lifecycle.viewModelScope
import com.tangerine.core.api.attractions.AttractionsRepo
import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.database.repo.AttractionsLocalRepoImpl
import com.tangerine.core.model.Attraction
import com.tangerine.core.model.AttractionsResp
import com.tangerine.core.model.AttractionsUiState
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.taipeitour.views.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AttractionsViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val attractionsRepo: AttractionsRepo
) : BaseViewModel(), KoinComponent {
    private val _attractionsUiState: MutableStateFlow<AttractionsUiState> =
        MutableStateFlow(AttractionsUiState(UiState.LOADING))
    val attractionUiState: StateFlow<AttractionsUiState> = _attractionsUiState
    val attractionsLocalRepo: AttractionsLocalRepoImpl by inject()

    init {
        getAttractions()
    }

    fun getAttractions(lang: String? = null, goNextPage: Boolean = false) {
        val data = attractionUiState.value.data


        val newPage = 1 //data.currentPage + (if (goNextPage) 1 else 0)
        val newLang = lang ?: data.currentLang

        viewModelScope.launch(dispatcher) {
            _attractionsUiState.let {
                it.value = it.value.updateLoading()

                attractionsRepo.getAttractions(lang = newLang, page = newPage).collect { api ->
                    when (api) {
                        is BaseRepo.ApiResponse.Success<*> -> {
                            attractionsLocalRepo.getAllSavedAttractions().collect { local ->
                                it.value = it.value.updateAttractions(
                                    newPage,
                                    newLang,
                                    updateBookmarked((api.response as AttractionsResp).data, local)
                                )
                            }
                        }

                        is BaseRepo.ApiResponse.Failure -> it.value =
                            it.value.updateError(api.throwable)
                    }
                }
            }
        }
    }

    fun updateNewLang(newValue: Language) {
        getAttractions(newValue.code)
    }

    private fun updateBookmarked(
        newAttrs: List<Attraction>?,
        savedAttrs: List<Attraction?>?
    ) = when {
        newAttrs == null -> mutableListOf()
        savedAttrs == null -> newAttrs
        else -> {
            val hashSet = HashSet(savedAttrs.map { it?.id })
            val rs = mutableListOf<Attraction>()

            println("Set: $hashSet")
            newAttrs.forEach {
                rs.add(it.apply {
                    isSaved = hashSet.contains(id)
                })
            }

            rs
        }
    }
}