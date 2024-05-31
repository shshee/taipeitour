package com.tangerine.taipeitour.views.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.LifecycleOwner
import com.tangerine.taipeitour.R
import com.tangerine.taipeitour.databinding.ActivityMainBinding
import com.tangerine.taipeitour.views.attractions.AttractionsFragment
import com.tangerine.taipeitour.views.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {
    private val vModel: MainActivityViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setUpViews()
        setUpObserves()
        setRootFragment(AttractionsFragment(), R.id.fragment_container_attraction)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
                com.tangerine.core.model.Language.values().forEachIndexed { i, e ->
                    menu.add(Menu.NONE, i, Menu.NONE, getString(e.title))
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                vModel.updateNewLang(com.tangerine.core.model.Language.getLanguage(menuItem.itemId))
                return true
            }
        })
    }

    private fun setUpObserves() {
        //On api failed
        vModel.failure.observe(this as LifecycleOwner) {
            showLoading(false)
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun showLoading(isShow: Boolean) {
        binding.progressBarCyclic.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}