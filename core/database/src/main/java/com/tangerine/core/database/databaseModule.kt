package com.tangerine.core.database

import androidx.room.Room
import com.tangerine.core.database.dao.AttractionsDao
import com.tangerine.core.database.repo.AttractionsLocalRepoImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
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