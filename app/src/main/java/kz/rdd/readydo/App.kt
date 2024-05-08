package kz.rdd

import android.app.Application
import kz.rdd.core.ui.BuildConfig
import kz.rdd.mamyq.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.Forest.plant

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            allModules
            if (BuildConfig.DEBUG) {
                plant(Timber.DebugTree())
            }
        }
    }
}