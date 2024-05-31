package com.tangerine.taipeitour.views.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.tangerine.core.ultis.navigator.FragmentNavigator

abstract class BaseActivity: AppCompatActivity(), LifecycleObserver, BaseUI {
    private lateinit var navigator: FragmentNavigator

    fun setRootFragment(rootFragment: Fragment?, rootLayout: Int) {
        navigator = FragmentNavigator(
            supportFragmentManager,
            rootLayout
        ).also {
            it.rootFragment = rootFragment
        }
    }

    abstract fun showLoading(isShow: Boolean)
    override fun getNavigator() = if(this::navigator.isInitialized) navigator else null
}