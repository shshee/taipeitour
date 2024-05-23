package com.tangerine.taipeitour.api.attractions

import android.content.Context
import com.tangerine.taipeitour.api.base.BaseRepo
import com.tangerine.taipeitour.models.AttractionsResp

class AttractionsRepo(
    context: Context,
    private val service: AttractionsService
) : BaseRepo(context) {
    fun getAttractions(lang: String, page: Int?=null, response: ApiResponse<AttractionsResp>) {
        request(service.getAttractions(lang, page), response)
    }
}