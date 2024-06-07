package com.tangerine.core.model

sealed interface AttractionsUiState : BaseUiState {
    val savedValue: AttractionsData?
        get() {
            return when (this) {
                is GotAttractions -> this.data
                else -> null //TODO applying finding cache
            }
        }

    object Loading : AttractionsUiState
    data class GotAttractions(val data: AttractionsData) : AttractionsUiState

    data class Failure(val throwable: Throwable) : AttractionsUiState
}

data class AttractionsData(
    var currentPage: Int = 1,
    var currentLang: String = Language.TAIWAN.code,
    var attractionsList: List<Attraction> = mutableListOf(),
)