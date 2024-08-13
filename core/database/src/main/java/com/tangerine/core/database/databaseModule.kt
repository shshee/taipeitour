package com.tangerine.core.database

import androidx.room.Room
import com.tangerine.core.database.datastore.DataStoreHolder
import com.tangerine.core.database.room.dao.AttractionsDao
import com.tangerine.core.database.room.repo.AttractionsLocalRepoImpl
import com.tangerine.core.database.room.TaipeiTourDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single { DataStoreHolder(get()) }

    single {
        Room.databaseBuilder(
            get(),
            TaipeiTourDatabase::class.java, "taipei-tour-database"
        ).build()
    }

    single<AttractionsDao> {
        get<TaipeiTourDatabase>().attractionsDao()
    }

    singleOf(::AttractionsLocalRepoImpl)
}