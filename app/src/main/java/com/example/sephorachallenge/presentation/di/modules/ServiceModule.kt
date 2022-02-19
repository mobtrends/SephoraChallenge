package com.example.sephorachallenge.presentation.di.modules

import android.content.Context
import com.example.sephorachallenge.data.ServiceBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class ServiceModule {

    @Provides
    internal fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    internal fun provideRetrofit(context: Context) = ServiceBuilder.getRetrofit(context)
}