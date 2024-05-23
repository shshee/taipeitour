package com.tangerine.taipeitour.api.attractions

import retrofit2.Retrofit

class AttractionsService(retrofit: Retrofit): AttractionsApi {
    private val attractionsApi by lazy { retrofit.create(AttractionsApi::class.java) }

    override fun getAttractions(lang: String, page: Int?) = attractionsApi.getAttractions(lang, page)
}