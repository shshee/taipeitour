package com.tangerine.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedAttractions")
data class SavedAttractionEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long,
)