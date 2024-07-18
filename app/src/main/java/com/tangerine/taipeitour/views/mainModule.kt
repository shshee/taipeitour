package com.tangerine.taipeitour.views

import com.tangerine.taipeitour.views.attractions.AttractionsAdapter
import com.tangerine.taipeitour.viewmodel.AttractionsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainModule = module {
    viewModel { AttractionsViewModel(dispatcher = Dispatchers.IO,get()) }
    factoryOf(::AttractionsAdapter)
}

