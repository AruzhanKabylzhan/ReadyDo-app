package kz.rdd.main.start

import kz.rdd.main.MainNavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val startModule = module {
    viewModelOf(::GlobalViewModel)
    viewModelOf(::MainNavigationViewModel)
}
