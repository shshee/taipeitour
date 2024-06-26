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
        val anyUpdates = when {
            isOnDiffLang -> AttractionsUpdate.NEW_LANG
            !isOnSamePage -> AttractionsUpdate.NEW_PAGE
            else -> AttractionsUpdate.NOTHING
        }

        return generateNewState(
            UiState.SUCCESS, AttractionsData(
                currentPage = newPage,
                currentLang = newLang,
                attractionsList = newData,
                updateType = anyUpdates.ordinal
            )
        )
    }

    fun updateLoading() = generateNewState(UiState.LOADING)

    fun updateError(ex: Throwable) = generateNewState(UiState.ERROR, data.apply {
        latestError = ex
    })

    fun handled() {
        data.latestError = null
        data.updateType = AttractionsUpdate.NOTHING.ordinal
    }

    private fun generateNewState(
        newState: UiState,
        newData: AttractionsData? = null,
    ) = AttractionsUiState(
        state = newState,
        data = newData ?: this.data
    )
}

data class AttractionsData(
    var currentPage: Int = 1,
    var currentLang: String = Language.TAIWAN.code,
    var attractionsList: MutableList<Attraction> = mutableListOf(),
    var latestError: Throwable? = null,
    var updateType: Int = AttractionsUpdate.NOTHING.ordinal,
)