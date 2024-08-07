package com.tangerine.taipeitour.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.async
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
    private val attractionsLocalRepo: AttractionsLocalRepoImpl by inject()

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
                            it.value = it.value.updateAttractions(
                                newPage,
                                newLang,
                                updateBookmarked(
                                    (api.response as AttractionsResp).data,
                                    attractionsLocalRepo.getAllSavedAttrIds()
                                )
                            )
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
        savedIds: List<Int?>?
    ) = when {
        newAttrs == null -> mutableListOf()
        savedIds == null -> newAttrs
        else -> {
            val hashSet = HashSet(savedIds)
            val rs = mutableListOf<Attraction>()

            newAttrs.forEach {
                rs.add(it.apply {
                    isSaved = hashSet.contains(id)
                })
            }

            rs
        }
    }

    suspend fun modifyBookmark(id: Int, doSaving: Boolean): Boolean {
        val job = viewModelScope.async(this.dispatcher) {
            try {
                val attraction =
                    attractionUiState.value.data.attractionsList.first { it.id == id }.copy().also {
                        it.isSaved = doSaving
                    }

                if (doSaving) return@async attractionsLocalRepo.saveAttraction(attraction)
                else return@async attractionsLocalRepo.removeSavedAttraction(id)
            } catch (ex: Exception) {
                false
            }
        }

        return job.await()
    }
}