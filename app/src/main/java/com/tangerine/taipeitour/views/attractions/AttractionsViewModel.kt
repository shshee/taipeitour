package com.tangerine.taipeitour.views.attractions

import androidx.lifecycle.viewModelScope
import com.tangerine.core.api.attractions.AttractionsRepo
import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsData
import com.tangerine.core.model.AttractionsResp
import com.tangerine.core.model.AttractionsUiState
import com.tangerine.core.model.Language
import com.tangerine.core.model.UiState
import com.tangerine.taipeitour.views.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttractionsViewModel(private val attractionsRepo: AttractionsRepo) : BaseViewModel() {
    private val _attractionsUiState: MutableStateFlow<AttractionsUiState> =
        MutableStateFlow(AttractionsUiState(UiState.LOADING))
    val attractionUiState: StateFlow<AttractionsUiState> = _attractionsUiState

    init {
        getAttractions()
    }

    fun getAttractions(lang: String? = null, goNextPage: Boolean = false) {
        val data = attractionUiState.value.data

        val newPage = 1 //data.currentPage + (if (goNextPage) 1 else 0)
        val newLang = lang ?: data.currentLang

        viewModelScope.launch {
            _attractionsUiState.let {
                //TODO save last list when paging is needed
                it.value = it.value.updateLoading()

                attractionsRepo.getAttractions(lang = newLang, page = newPage).collect { rs ->
                    it.value = when (rs) {
                        is BaseRepo.ApiResponse.Success<*> -> {
                            it.value.updateAttractions(
                                newPage,
                                newLang,
                                (rs.response as AttractionsResp).data?.toMutableList()
                                    ?: mutableListOf()
                            )
                        }

                        is BaseRepo.ApiResponse.Failure -> it.value.updateError(rs.throwable)
                    }
                }
            }
        }
    }

    fun updateNewLang(newValue: Language) {
        getAttractions(newValue.code)
    }
}