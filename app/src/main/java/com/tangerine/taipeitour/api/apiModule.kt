package com.tangerine.taipeitour.api

import okhttp3.OkHttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.tangerine.taipeitour.api.attractions.AttractionsService
import com.tangerine.taipeitour.api.attractions.AttractionsRepo
import okhttp3.logging.HttpLoggingInterceptor

val apiModule = module {
    singleOf(::retrofit)

    factoryOf(::AttractionsService)
    factoryOf(::AttractionsRepo)
}

private fun retrofit(): Retrofit {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)

    return Retrofit.Builder()
        .baseUrl("https://www.travel.taipei/open-api/")
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}