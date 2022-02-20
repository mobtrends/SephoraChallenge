package com.example.sephorachallenge.presentation.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductsApiService
import com.example.data.repository.ProductsRepositoryImpl
import com.example.domain.repository.ProductsRepository
import com.example.sephorachallenge.data.database.repository.ProductsDatabaseRepositoryImpl
import com.example.sephorachallenge.domain.mapper.DisplayableProductTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import com.example.sephorachallenge.presentation.viewmodels.product.ProductsViewModel
import com.example.sephorachallenge.presentation.fragment.product.ProductsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Inject

@Module(includes = [ProductsModule.BindingModule::class])
class ProductsModule(private val fragment: ProductsFragment) {

    @Provides
    fun provideService(retrofit: Retrofit): ProductsApiService =
        retrofit.create(ProductsApiService::class.java)

    @Module
    internal abstract class BindingModule {

        @Binds
        abstract fun bindRepository(repository: ProductsRepositoryImpl): ProductsRepository

        @Binds
        abstract fun bindProductDatabaseRepository(repository: ProductsDatabaseRepositoryImpl): ProductsDatabaseRepository
    }

    @Provides
    fun provideViewModel(factory: ProductsViewModelFactory): ProductsViewModel {
        return ViewModelProvider(fragment, factory)[ProductsViewModel::class.java]
    }
}

class ProductsViewModelFactory @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val productsDatabaseRepository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ProductsViewModel(
            productsRepository,
            productsDatabaseRepository,
            transformer,
            dispatcher
        ) as T
}