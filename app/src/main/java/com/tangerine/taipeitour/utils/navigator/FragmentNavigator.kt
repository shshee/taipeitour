package com.tangerine.taipeitour.utils.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tangerine.taipeitour.R
import com.tangerine.taipeitour.models.AnimType
import java.util.*

/**
 * Created by thongph on 11.28.2017
 *
 *
 * Last modified on 11.28.2017
 */
class FragmentNavigator(
    private val mFragmentManager: FragmentManager,
    @field:IdRes @param:IdRes private val mDefaultContainer: Int
) {
    private var mRootFragmentTag: String? = null

    /**
     * @return the current active fragment. If no fragment is active it return null.
     */
    val activeFragment: Fragment?
        get() {
            val tag: String? = if (mFragmentManager.backStackEntryCount == 0) {
                mRootFragmentTag
            } else {
                mFragmentManager
                    .getBackStackEntryAt(mFragmentManager.backStackEntryCount - 1).name
            }
            return mFragmentManager.findFragmentByTag(tag)
        }

    /**
     * Pushes the fragment, and add it to the history (BackStack) if you have set a default animation
     * it will be added to the transaction.
     *
     * @param fragment The fragment which will be added
     * @param animType Transition for fragment
     * @return started from root
     */
    fun goTo(fragment: Fragment, animType: AnimType? = null): Boolean {
        val startedFromRoot = mFragmentManager.backStackEntryCount == 0

        mFragmentManager.beginTransaction().also {
            startAnim(it, animType)

            it.addToBackStack(getTag(fragment))
            it.add(mDefaultContainer, fragment, getTag(fragment))
            it.commit()
        }
        mFragmentManager.executePendingTransactions()
        return startedFromRoot
    }

    /**
     * This is just a helper method which returns the simple name of the fragment.
     *
     * @param fragment That get added to the history (BackStack)
     * @return The simple name of the given fragment
     */
    private fun getTag(fragment: Fragment?): String {
        return fragment!!.javaClass.simpleName
    }
    /**
     * Get root fragment
     *
     * @return
     */
    /**
     * Set the new root fragment. If there is any entry in the history (BackStack) it will be
     * deleted.
     *
     * @param rootFragment The new root fragment
     */
    var rootFragment: Fragment?
        get() = mFragmentManager.findFragmentByTag(mRootFragmentTag)
        set(rootFragment) {
            if (rootFragment == null) return
            if (size > 0) {
                clearHistory()
            }
            mRootFragmentTag = getTag(rootFragment)
            replaceFragment(rootFragment)
        }

    /**
     * Replace the current fragment with the given one, without to add it to backstack. So when the
     * users navigates away from the given fragment it will not appaer in the history.
     *
     * @param fragment The new fragment
     */
    private fun replaceFragment(fragment: Fragment) {
        mFragmentManager.beginTransaction()
            .replace(mDefaultContainer, fragment, getTag(fragment))
            .commitNow()
    }

    fun removeFragment(fragment: Fragment) {
        val trans = mFragmentManager.beginTransaction()
        trans.remove(fragment)
        trans.commit()
        mFragmentManager.popBackStack()
    }

    /**
     * Goes one entry back in the history
     * @return reached root
     */
    fun goOneBack(): Boolean {
        mFragmentManager.popBackStackImmediate()
        return mFragmentManager.backStackEntryCount == 0
    }

    /**
     * Goes one entry back in the history
     */
    fun goOneBackTo(tagFragment: String) {
        var i = size - 1
        while (size >= 1) {
            if (Objects.requireNonNull(mFragmentManager.getBackStackEntryAt(i).name) != tagFragment) {
                goOneBack()
                i--
            } else {
                return
            }
        }
    }

    fun checkIfFragmentExisted(tagFragment: String): Boolean {
        for (i in 0 until mFragmentManager.backStackEntryCount) {
            if (Objects.requireNonNull(mFragmentManager.getBackStackEntryAt(i).name) == tagFragment) return true
        }
        return false
    }

    /**
     * @return The current size of the history (BackStack)
     */
    val size: Int
        get() = mFragmentManager.backStackEntryCount

    /**
     * Goes the whole history back until to the first fragment in the history. It would be the same if
     * the user would click so many times the back button until he reach the first fragment of the
     * app.
     */
    fun goToRoot() {
        while (size >= 1) {
            goOneBack()
        }
    }

    /**
     * Clears the whole history so it will no BackStack entry there any more.
     */
    private fun clearHistory() {
        try {
            while (mFragmentManager.fragments.isNotEmpty()) {
                mFragmentManager.popBackStackImmediate()
            }
        } catch (ignored: IllegalStateException) {
        }
    }

    private fun startAnim(trans: FragmentTransaction, animType: AnimType?) {
        when (animType) {
            AnimType.SLIDE_TOP -> {
                trans.setCustomAnimations(
                    R.anim.slide_in_top,  // enter
                    R.anim.slide_out_top,  // exit
                    R.anim.slide_in_top,   // popEnter
                    R.anim.slide_out_top // popExit
                )
            }

            AnimType.SLIDE_BOTTOM -> {
                trans.setCustomAnimations(
                    R.anim.slide_in_bottom,  // enter
                    R.anim.slide_out_top,  // exit
                    R.anim.slide_in_top,   // popEnter
                    R.anim.slide_out_bottom  // popExit
                )
            }

            AnimType.SLIDE_LEFT -> {
                trans.setCustomAnimations(
                    R.anim.slide_in_left,  // enter
                    R.anim.slide_out_top,  // exit
                    R.anim.slide_in_top,   // popEnter
                    R.anim.slide_out_left  // popExit
                )
            }

            AnimType.SLIDE_RIGHT -> {
                trans.setCustomAnimations(
                    R.anim.slide_in_right,  // enter
                    R.anim.slide_out_top,  // exit
                    R.anim.slide_in_top,   // popEnter
                    R.anim.slide_out_right,  // popExit
                )
            }

            AnimType.FADE -> {
                trans.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }

            else -> {}
        }
    }
}