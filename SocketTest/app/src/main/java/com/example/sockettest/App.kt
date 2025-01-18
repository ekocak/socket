package com.example.sockettest

import android.app.Application
import android.content.Context
import android.provider.Settings
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()


    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}
