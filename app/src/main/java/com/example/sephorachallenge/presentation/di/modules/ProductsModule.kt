package com.example.sephorachallenge.presentation.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sephorachallenge.presentation.fragment.ProductsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Inject

@Module(includes = [ProductsModule.BindingModule::class])
class ProductsModule(private val fragment: ProductsFragment) {

    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(ProductsApiService::class.java)

    @Module
    internal abstract class BindingModule {

        @Binds
        abstract fun bindRepository(repository: ProductsRepositoryImpl): ProductsRepository

    }

    @Provides
    fun provideViewModel(factory: ProductsViewModelFactory): ProductsViewModel {
        return ViewModelProvider(fragment, factory).get(ProductsViewModel::class.java)
    }
}

class ProductsViewModelFactory @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductsViewModel(productsRepository, dispatcher)
    } as T
}