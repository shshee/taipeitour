package com.tangerine.taipeitour.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.tangerine.taipeitour.api.attractions.AttractionsRepo
import com.tangerine.taipeitour.api.base.BaseRepo
import com.tangerine.taipeitour.models.Attraction
import com.tangerine.taipeitour.models.AttractionsResp
import com.tangerine.taipeitour.models.Language
import com.tangerine.taipeitour.views.base.BaseViewModel

class MainActivityViewModel(private val attractionsRepo: AttractionsRepo) : BaseViewModel() {
    private var page = 1

    private val _attractions: MutableLiveData<List<Attraction>> = MutableLiveData()
    val attractions: LiveData<List<Attraction>> = _attractions

    private val _currentLang: MutableLiveData<Language> = MutableLiveData(Language.TAIWAN)
    val currentLang: LiveData<Language> = _currentLang

    fun getAttractions(lang: String?, goNextPage: Boolean) {
        if (lang == null) return

        val newPage = page + (if (goNextPage) 1 else 0)
        attractionsRepo.getAttractions(
            lang = lang,
            page = newPage,
            object : BaseRepo.ApiResponse<AttractionsResp> {
                override fun onSuccess(result: AttractionsResp) {
                    result.data?.let {
                        _attractions.value = it
                        page = newPage
                    }
                }

                override fun onFailure(message: String?) {
                    handleFailure(message)
                }
            })
    }

    fun updateNewLang(newValue: Language) {
        page = 1
        _currentLang.value = newValue
    }
}