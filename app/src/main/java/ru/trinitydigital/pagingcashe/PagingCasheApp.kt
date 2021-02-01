package ru.trinitydigital.pagingcashe

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.trinitydigital.pagingcashe.di.appModules

class PagingCasheApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PagingCasheApp)
            modules(appModules)
        }
    }
}