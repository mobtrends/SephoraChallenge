package com.example.sephorachallenge.presentation.di.components

import android.content.Context
import com.example.sephorachallenge.presentation.di.modules.ApplicationModule
import com.example.sephorachallenge.presentation.di.modules.ProductsDatabaseRepositoryModule
import com.example.sephorachallenge.presentation.di.modules.ServiceModule
import dagger.Component

@Component(modules = [ApplicationModule::class, ServiceModule::class, ProductsDatabaseRepositoryModule::class])
interface ApplicationComponent {
    fun context(): Context
}