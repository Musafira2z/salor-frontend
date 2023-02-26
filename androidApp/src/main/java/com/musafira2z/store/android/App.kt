package com.musafira2z.store.android

import android.app.Application
import com.musafira2z.store.android.di.androidModule
import com.musafira2z.store.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + androidModule)
        }
    }
}