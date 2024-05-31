package com.tangerine.core.api.attractions

import android.content.Context
import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsResp

class AttractionsRepo(
    context: Context,
    private val service: AttractionsService
) : BaseRepo(context) {
    fun getAttractions(lang: String, page: Int?=null, response: ApiResponse<AttractionsResp>) {
        request(service.getAttractions(lang, page), response)
    }
}