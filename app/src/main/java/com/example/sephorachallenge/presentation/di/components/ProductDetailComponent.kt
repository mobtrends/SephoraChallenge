package com.example.sephorachallenge.presentation.di.components

import com.example.sephorachallenge.presentation.di.modules.ProductDetailModule
import com.example.sephorachallenge.presentation.di.modules.ProductsDatabaseRepositoryModule
import com.example.sephorachallenge.presentation.di.modules.ServiceModule
import com.example.sephorachallenge.presentation.fragment.productdetail.ProductDetailFragment
import dagger.Component

@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ProductDetailModule::class, ServiceModule::class, ProductsDatabaseRepositoryModule::class]
)
interface ProductDetailComponent {
    fun inject(fragment: ProductDetailFragment)
}