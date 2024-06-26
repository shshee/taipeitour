package com.tangerine.taipeitour.views.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver

abstract class BaseActivity: AppCompatActivity(), LifecycleObserver {
    abstract fun showLoading(isShow: Boolean)

//    private lateinit var navigator: FragmentNavigator
//
//    fun setRootFragment(rootFragment: Fragment?, rootLayout: Int) {
//        navigator = FragmentNavigator(
//            supportFragmentManager,
//            rootLayout
//        ).also {
//            it.rootFragment = rootFragment
//        }
//    }
//
//    override fun getNavigator() = if(this::navigator.isInitialized) navigator else null
}