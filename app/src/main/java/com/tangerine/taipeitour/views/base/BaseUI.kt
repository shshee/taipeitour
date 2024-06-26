package com.tangerine.taipeitour.views.base

import androidx.fragment.app.Fragment

/**
 * Implementation of navigation through navigator
 */
interface BaseUI {
    fun getNavigator(): com.tangerine.core.ultis.navigator.FragmentNavigator?

    fun goTo(destination: Fragment, animType: com.tangerine.core.model.AnimType?) {
        getNavigator()?.goTo(destination, animType)
    }

    fun goOneBack() {
        getNavigator()?.goOneBack()
    }
}