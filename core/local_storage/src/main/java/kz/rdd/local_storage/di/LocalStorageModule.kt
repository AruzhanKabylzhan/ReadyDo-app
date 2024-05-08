package kz.rdd.core.local_storage.di

import kz.rdd.core.local_storage.data.LocalStorageImpl
import kz.rdd.core.local_storage.domain.LocalStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localStorageModule = module {
    single<LocalStorage> { LocalStorageImpl(androidContext()) }
}
