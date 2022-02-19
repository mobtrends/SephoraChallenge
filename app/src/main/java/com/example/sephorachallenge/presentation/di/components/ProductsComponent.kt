package com.example.sephorachallenge.presentation.di.components

import com.example.sephorachallenge.presentation.di.modules.ProductsModule
import com.example.sephorachallenge.presentation.di.modules.ServiceModule
import com.example.sephorachallenge.presentation.fragment.ProductsFragment
import dagger.Component

@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ProductsModule::class, ServiceModule::class]
)
interface ProductsComponent {
    fun inject(fragment: ProductsFragment)
}