package com.example.sephorachallenge.presentation.di.modules

import android.content.Context
import com.example.sephorachallenge.SephoraChallengeApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: SephoraChallengeApplication) {

    @Provides
    internal fun provideContext(): Context {
        return application.applicationContext
    }
}