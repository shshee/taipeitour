package com.tangerine.core.database.repo

import com.tangerine.core.model.Attraction
import kotlinx.coroutines.flow.Flow

interface AttractionsLocalRepo {
    fun listenToAllSavedAttractions(): Flow<List<Attraction?>> //Used to collect flow when changes happens
    suspend fun getAllSavedAttrIds(): List<Int?> //Used to get all once
    suspend fun removeSavedAttraction(id: Int): Boolean
    suspend fun saveAttraction(attraction: Attraction): Boolean
}