package com.tangerine.core.model

class AttractionsUiState(var state: UiState, var data: AttractionsData = AttractionsData()) {
    fun updateAttractions(
        newPage: Int,
        attractions: List<Attraction>
    ): AttractionsUiState {
        val isOnSamePage = newPage == data.currentPage

        val newData = if (isOnSamePage) attractions.toMutableList() else data.attractionsList.also {
            it.addAll(attractions)
        }

        return generateNewState(
            UiState.SUCCESS, AttractionsData(
                currentPage = newPage,
                attractionsList = newData
            )
        )
    }

    fun updateLoading() = generateNewState(UiState.LOADING)

    fun updateError(ex: Throwable, isNewPage: Boolean) =
        generateNewState(UiState.ERROR, data.apply {
            latestError = ex

            //Clear latest data if new language was selected
            if (!isNewPage) data.attractionsList = mutableListOf()
        })

    fun handleError(): String? {
        val current = data.latestError?.message
        data.latestError = null

        return current
    }

    private fun generateNewState(
        newState: UiState,
        newData: AttractionsData? = null,
    ) = AttractionsUiState(
        state = newState,
        data = newData ?: this.data
    )
}

class AttractionsData(
    var currentPage: Int = 1,
    var attractionsList: MutableList<Attraction> = mutableListOf()
) {
    internal var latestError: Throwable? = null
        set(value) {
            field = value
        }
}