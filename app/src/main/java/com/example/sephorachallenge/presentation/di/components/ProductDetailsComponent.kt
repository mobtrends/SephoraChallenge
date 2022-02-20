package com.example.sephorachallenge.presentation.di.components

import com.example.sephorachallenge.presentation.di.modules.ProductDetailsModule
import com.example.sephorachallenge.presentation.di.modules.ProductsDatabaseRepositoryModule
import com.example.sephorachallenge.presentation.di.modules.ServiceModule
import com.example.sephorachallenge.presentation.fragment.productdetail.ProductDetailsFragment
import dagger.Component

@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ProductDetailsModule::class, ServiceModule::class, ProductsDatabaseRepositoryModule::class]
)
interface ProductDetailsComponent {
    fun inject(fragment: ProductDetailsFragment)
}