package com.tangerine.core.api.attractions

import com.tangerine.core.api.base.BaseRepo

class AttractionsRepo(
    private val service: AttractionsService
) : BaseRepo() {
    fun getAttractions(lang: String, page: Int?=null) = request(service.getAttractions(lang, page))
}