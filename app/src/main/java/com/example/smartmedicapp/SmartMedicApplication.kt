package com.example.smartmedicapp

import android.app.Application
import android.content.res.Resources
import timber.log.Timber

class SmartMedicApplication : Application()  {

    companion object {
        lateinit var instance: Application
        lateinit var resourses: Resources
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
        Timber.plant(Timber.DebugTree())

    }


}