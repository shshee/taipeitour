package com.tangerine.core.api.attractions

import com.tangerine.core.model.AttractionsResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface AttractionsApi {
    companion object {
        private const val LANG = "lang"
        private const val ATTRACTIONS_ALL = "{$LANG}/Attractions/All"
    }

    @Headers("Accept: application/json")
    @GET(ATTRACTIONS_ALL)
    fun getAttractions(@Path(LANG) lang: String, @Query("page") page: Int?=null): Call<AttractionsResp>
}