package kz.rdd.navigate.di

import kz.rdd.forefront_nav.ForefrontDestinationsDelegate
import kz.rdd.navigate.ForefrontDestinationsDelegateImpl
import org.koin.dsl.module

val navigateModule = module {
    factory<ForefrontDestinationsDelegate>() {
        ForefrontDestinationsDelegateImpl()
    }
}