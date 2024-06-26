package com.tangerine.taipeitour.views.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver

abstract class BaseFragment<T> : Fragment(), LifecycleObserver {
    protected var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun showLoading(isShow: Boolean) {
        if (activity is BaseActivity) (activity as BaseActivity).showLoading(isShow)
    }

//    override fun getNavigator() =
//        if (activity is BaseActivity) (activity as BaseActivity).getNavigator() else null
}