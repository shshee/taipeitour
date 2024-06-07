package com.tangerine.taipeitour.views

import com.tangerine.taipeitour.views.attractions.AttractionsAdapter
import com.tangerine.taipeitour.views.attractions.AttractionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::AttractionsViewModel)
    factoryOf(::AttractionsAdapter)
}

