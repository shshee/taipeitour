package com.tangerine.core.database.repo

import com.tangerine.core.model.Attraction
import kotlinx.coroutines.flow.Flow

interface AttractionsLocalRepo {
    fun getAllSavedAttractions(): Flow<List<Attraction?>>
    suspend fun removeSavedAttraction(id: Int): Boolean
    suspend fun saveAttraction(attraction: Attraction): Boolean
}