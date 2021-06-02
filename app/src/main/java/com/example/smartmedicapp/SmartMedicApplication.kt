package com.example.smartmedicapp

import android.app.Application
import timber.log.Timber

class SmartMedicApplication : Application()  {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

    }
}