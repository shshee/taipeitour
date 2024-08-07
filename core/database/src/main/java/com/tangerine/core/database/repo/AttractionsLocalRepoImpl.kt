package com.tangerine.core.database.repo

import com.tangerine.core.database.dao.AttractionsDao
import com.tangerine.core.database.entity.SavedAttractionEntity
import com.tangerine.core.model.Attraction
import com.tangerine.core.ultis.toJson
import com.tangerine.core.ultis.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AttractionsLocalRepoImpl(private val attractionsDao: AttractionsDao) : AttractionsLocalRepo {
    val INSERTED_FAILED = 1L

    override fun listenToAllSavedAttractions(): Flow<List<Attraction?>> =
        attractionsDao.listenAllSavedAttractions().map { list ->
            list.sortedByDescending { it.timeStamp }.map { item ->
                toObject<Attraction>(item.data)
            }
        }

    override suspend fun getAllSavedAttrIds(): List<Int?> = attractionsDao.getAllSavedAttrIds()

    override suspend fun removeSavedAttraction(id: Int) =
        attractionsDao.removeSavedAttraction(id) != 0 //Row effected

    override suspend fun saveAttraction(attraction: Attraction): Boolean {
        attractionsDao.saveAttraction(
            SavedAttractionEntity(
                id = attraction.id,
                timeStamp = System.currentTimeMillis(),
                data = toJson(attraction)
            )
        ).let {
            return it != INSERTED_FAILED
        }
    }
}