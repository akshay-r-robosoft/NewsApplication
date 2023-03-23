package com.newsapplication

import android.app.Application
import com.newsapplication.di.networkModule
import com.newsapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewsApplication)
            androidLogger()
            modules(listOf(networkModule, viewModelModule))
        }
    }
}