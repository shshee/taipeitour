package com.tangerine.taipeitour.views.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import com.tangerine.taipeitour.R
import com.tangerine.taipeitour.databinding.ActivityMainBinding
import com.tangerine.taipeitour.viewmodel.AttractionsViewModel
import com.tangerine.taipeitour.views.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainXmlActivity : BaseActivity() {
    private val vModel: AttractionsViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
                com.tangerine.core.model.Language.values().forEachIndexed { i, e ->
                    menu.add(Menu.NONE, i, Menu.NONE, getString(e.title))
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                vModel.updateNewLang(com.tangerine.core.model.Language.getLanguageFromOrdinal(menuItem.itemId))
                return true
            }
        })
    }

    override fun showLoading(isShow: Boolean) {
        binding.progressBarCyclic.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}