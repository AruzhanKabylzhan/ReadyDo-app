package kz.rdd.store.di

import kz.rdd.core.utils.getEncryptedSharedPreferences
import kz.rdd.store.UserInteractionStore
import kz.rdd.store.UserSessionCleaner
import kz.rdd.store.UserStore
import kz.rdd.store.appidentifiers.AppIdentifiers
import kz.rdd.store.appidentifiers.AppIdentifiersStorage
import kz.rdd.store.appidentifiers.MockAppIdentifiersStorage
import kz.rdd.store.appidentifiers.impl.AndroidAppIdentifiersImpl
import kz.rdd.store.appidentifiers.impl.AndroidAppIdentifiersStorageImpl
import kz.rdd.store.appidentifiers.impl.AndroidMockAppIdentifiersStorageImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storeModule = module {
    factory {
        androidContext().getEncryptedSharedPreferences("user_prefs")
    }
    singleOf(::UserStore) bind UserSessionCleaner::class
    singleOf(::UserInteractionStore)

    singleOf(::AndroidAppIdentifiersImpl) bind AppIdentifiers::class
    singleOf(::AndroidAppIdentifiersStorageImpl) bind AppIdentifiersStorage::class
    singleOf(::AndroidMockAppIdentifiersStorageImpl) bind MockAppIdentifiersStorage::class
}
