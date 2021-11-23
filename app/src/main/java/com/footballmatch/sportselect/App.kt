package com.footballmatch.sportselect

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        var appRun: Boolean = true
    }
}