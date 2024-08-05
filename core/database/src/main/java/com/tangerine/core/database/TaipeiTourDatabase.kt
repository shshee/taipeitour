package com.tangerine.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tangerine.core.database.dao.AttractionsDao
import com.tangerine.core.database.entity.SavedAttractionEntity
import com.tangerine.core.database.repo.AttractionsLocalRepoImpl

@Database(entities = [SavedAttractionEntity::class], version = 1)
abstract class  TaipeiTourDatabase: RoomDatabase() {
    abstract fun attractionsDao(): AttractionsDao
}

object DatabaseImpl {
    private lateinit var database: TaipeiTourDatabase
    val attractionsLocalRepoImpl by lazy { AttractionsLocalRepoImpl(database.attractionsDao()) }

    fun initDatabase(applicationContext: Context) {
        database = Room.databaseBuilder(
            applicationContext,
            TaipeiTourDatabase::class.java, "taipei-tour-database"
        ).build()
    }
}