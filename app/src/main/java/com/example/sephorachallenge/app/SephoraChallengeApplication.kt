package com.example.sephorachallenge.app

import android.app.Application
import com.example.sephorachallenge.presentation.di.components.ApplicationComponent
import com.example.sephorachallenge.presentation.di.components.DaggerApplicationComponent
import com.example.sephorachallenge.presentation.di.modules.ApplicationModule

class SephoraChallengeApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
    }
}