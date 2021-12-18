package com.tejas.relifemedicalsystemtest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "(%s:%s) @%s",
                    element.fileName,
                    element.lineNumber,
                    element.methodName
                )
            }
        })
    }
}