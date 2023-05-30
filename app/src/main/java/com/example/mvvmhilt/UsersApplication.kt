package com.example.mvvmhilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Every project that uses Hilt requires an application class annotated with @HiltAndroidApp
 */
@HiltAndroidApp
class UsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Logging library, do not need to specify tags
        //Conventional way to write log is Log.d("TAG","Message") while using timber is Timber.d("Message")
        Timber.plant(Timber.DebugTree())
    }
}