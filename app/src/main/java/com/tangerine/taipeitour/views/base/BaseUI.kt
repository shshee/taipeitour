package com.tangerine.taipeitour.views.base

import androidx.fragment.app.Fragment
import com.tangerine.taipeitour.models.AnimType
import com.tangerine.taipeitour.utils.navigator.FragmentNavigator

interface BaseUI {
    fun getNavigator(): FragmentNavigator?

    fun goTo(destination: Fragment, animType: AnimType?) {
        getNavigator()?.goTo(destination, animType)
    }

    fun goOneBack() {
        getNavigator()?.goOneBack()
    }
}