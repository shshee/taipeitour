package com.tangerine.core.model

class AttractionsUiState(var state: UiState, var data: AttractionsData = AttractionsData()) {
    fun updateAttractions(
        newPage: Int,
        isOnDiffLang: Boolean,
        attractions: List<Attraction>
    ): AttractionsUiState {
        val isOnSamePage = newPage == data.currentPage

        val newData = if (isOnDiffLang || isOnSamePage) attractions.toMutableList() else data.attractionsList.also {
            it.addAll(attractions)
        }
        val anyUpdates = when {
            isOnDiffLang -> AttractionsUpdate.NEW_LANG
            !isOnSamePage -> AttractionsUpdate.NEW_PAGE
            else -> AttractionsUpdate.NOTHING
        }

        return generateNewState(
            UiState.SUCCESS, AttractionsData(
                currentPage = newPage,
                attractionsList = newData,
                updateType = anyUpdates.ordinal
            )
        )
    }

    fun updateLoading() = generateNewState(UiState.LOADING)

    fun updateError(ex: Throwable) = generateNewState(UiState.ERROR, data.apply {
        latestError = ex
    })

    fun handleError(): String? {
        val current = data.latestError?.message

        state = UiState.IDLE
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
    var attractionsList: MutableList<Attraction> = mutableListOf(),
    var updateType: Int = AttractionsUpdate.NOTHING.ordinal,
) {
    internal var latestError: Throwable? = null
        set(value) {
            field = value
            if (value == null) updateType = AttractionsUpdate.NOTHING.ordinal
        }
}