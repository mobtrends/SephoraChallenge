package com.example.sephorachallenge.presentation.di.modules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sephorachallenge.data.database.repository.ProductsDatabaseRepositoryImpl
import com.example.sephorachallenge.domain.mapper.DisplayableProductDetailsTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import com.example.sephorachallenge.presentation.viewmodels.productdetail.ProductDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@Module(includes = [ProductDetailsModule.BindingModule::class])
class ProductDetailsModule(private val fragment: Fragment, private val productId: Int) {

    @Provides
    @Named("productId")
    fun provideProductId() = productId

    @Provides
    fun provideViewModel(factory: ProductDetailViewModelFactory): ProductDetailsViewModel {
        return ViewModelProvider(fragment, factory)[ProductDetailsViewModel::class.java]
    }

    @Module
    internal abstract class BindingModule {
        @Binds
        abstract fun bindProductDatabaseRepository(repository: ProductsDatabaseRepositoryImpl): ProductsDatabaseRepository
    }
}

class ProductDetailViewModelFactory @Inject constructor(
    @Named("productId") private val productId: Int,
    private val repository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductDetailsTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ProductDetailsViewModel(productId, repository, transformer, dispatcher) as T
}