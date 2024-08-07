package com.tangerine.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tangerine.core.database.entity.SavedAttractionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttractionsDao {
    @Query("SELECT id FROM SavedAttractions")
    fun getAllSavedAttrIds(): List<Int?>

    @Query("SELECT * FROM SavedAttractions")
    fun listenAllSavedAttractions(): Flow<List<SavedAttractionEntity>>

    @Query("SELECT * FROM SavedAttractions WHERE id = :id")
    suspend fun getSavedAttractions(id: Int): SavedAttractionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAttraction(attraction: SavedAttractionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAttractions(vararg attractions: SavedAttractionEntity): LongArray //Succeeded if longArray.size == input.size

    @Query("DELETE FROM SavedAttractions WHERE id = :id")
    suspend fun removeSavedAttraction(id: Int): Int
}