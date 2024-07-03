package com.tangerine.core.api.attractions

import com.tangerine.core.api.base.BaseRepo
import kotlinx.coroutines.CoroutineDispatcher

class AttractionsRepo(
    dispatcher: CoroutineDispatcher,
    private val service: AttractionsService
) : BaseRepo(dispatcher) {
    fun getAttractions(lang: String, page: Int?=null) = request(service.getAttractions(lang, page))
}