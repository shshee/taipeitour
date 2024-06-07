package com.tangerine.taipeitour.views.attractions

import androidx.lifecycle.viewModelScope
import com.tangerine.core.api.attractions.AttractionsRepo
import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsData
import com.tangerine.core.model.AttractionsResp
import com.tangerine.core.model.AttractionsUiState
import com.tangerine.core.model.Language
import com.tangerine.taipeitour.views.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttractionsViewModel(private val attractionsRepo: AttractionsRepo) : BaseViewModel() {
    private val _attractionsUiState: MutableStateFlow<AttractionsUiState> =
        MutableStateFlow(AttractionsUiState.Loading)
    val attractionUiState: StateFlow<AttractionsUiState> = _attractionsUiState

    init {
        getAttractions()
    }

    fun getAttractions(lang: String? = null, goNextPage: Boolean = false) {
        val data = attractionUiState.value.savedValue ?: AttractionsData()

        val newPage = 1 //data.currentPage + (if (goNextPage) 1 else 0)
        val newLang = lang ?: data.currentLang

        viewModelScope.launch {
            //TODO save last list when paging is needed
            _attractionsUiState.value = AttractionsUiState.Loading

            attractionsRepo.getAttractions(lang = newLang, page = newPage).collect {
                _attractionsUiState.value = when (it) {
                    is BaseRepo.ApiReponse.Success<*> -> {
                        AttractionsUiState.GotAttractions(
                            AttractionsData(
                                currentPage = newPage,
                                currentLang = newLang,
                                attractionsList = (it.response as AttractionsResp).data
                                    ?: mutableListOf()
                            )
                        )
                    }

                    is BaseRepo.ApiReponse.Failure -> AttractionsUiState.Failure(it.throwable)
                }
            }
        }
    }

    fun updateNewLang(newValue: Language) {
        getAttractions(newValue.code)
    }
}