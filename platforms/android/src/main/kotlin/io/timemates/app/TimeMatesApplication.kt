package io.timemates.app

import android.app.Application
import org.koin.core.context.startKoin

class TimeMatesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // defaultModule()
        }
    }
}