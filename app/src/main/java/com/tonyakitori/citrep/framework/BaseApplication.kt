package com.tonyakitori.citrep.framework

import android.app.Application
import com.tonyakitori.citrep.BuildConfig
import com.tonyakitori.citrep.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@BaseApplication)
            modules(allModules)
        }
    }

}