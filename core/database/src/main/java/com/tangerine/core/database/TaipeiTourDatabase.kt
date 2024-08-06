package com.tangerine.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tangerine.core.database.dao.AttractionsDao
import com.tangerine.core.database.entity.SavedAttractionEntity

@Database(entities = [SavedAttractionEntity::class], version = 1)
abstract class  TaipeiTourDatabase: RoomDatabase() {
    abstract fun attractionsDao(): AttractionsDao
}