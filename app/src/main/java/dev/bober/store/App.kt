package dev.bober.store

import android.app.Application
import dev.bober.store.di.dataModule
import dev.bober.store.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            logger(AndroidLogger(level = Level.INFO))
            workManagerFactory()

            modules(
                modules = listOf(
                    dataModule,
                    presentationModule
                )
                //TODO:
            )
        }
    }
}