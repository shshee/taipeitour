package com.tangerine.core.model

class AttractionsUiState(var state: UiState, var data: AttractionsData = AttractionsData()) {
    fun updateAttractions(
        newPage: Int,
        newLang: String,
        attractions: MutableList<Attraction>
    ): AttractionsUiState {
        val isOnSamePage = newPage == data.currentPage
        val isOnDiffLang = newLang != data.currentLang

        val newData = if (isOnDiffLang || isOnSamePage) attractions else data.attractionsList.also {
            it.addAll(attractions)
        }

        return generateNewState(
            UiState.SUCCESS, AttractionsData(
                currentPage = newPage,
                currentLang = newLang,
                attractionsList = newData
            )
        )
    }

    fun updateLoading() = generateNewState(UiState.LOADING)

    fun updateError(ex: Throwable) = generateNewState(UiState.ERROR, data.apply {
        latestError = ex
    })

    private fun generateNewState(
        newState: UiState,
        newData: AttractionsData? = null,
    ) = AttractionsUiState(
        state = newState,
        data = newData ?: this.data
    )
}

data class AttractionsData(
    var latestError: Throwable? = null,
    var currentPage: Int = 1,
    var currentLang: String = Language.TAIWAN.code,
    var attractionsList: MutableList<Attraction> = mutableListOf(),
)