package com.tangerine.core.api.attractions

import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsResp
import kotlinx.coroutines.CoroutineDispatcher

class AttractionsRepo(
    dispatcher: CoroutineDispatcher,
    private val service: AttractionsService
) : BaseRepo(dispatcher) {
    suspend fun getAttractions(lang: String, page: Int? = null) = request {
        service.getAttractions(lang, page)
    }
}