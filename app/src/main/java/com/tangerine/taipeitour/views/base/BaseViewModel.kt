package com.tangerine.taipeitour.views.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    //Update to seal classes to handle many type of errors
    private val _failure: MutableLiveData<String?> = MutableLiveData()
    val failure: LiveData<String?> = _failure

    protected fun handleFailure(failure: String?) {
        _failure.value = failure
    }
}