package com.example.sephorachallenge.presentation.viewmodels.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.ProductsRepository
import com.example.sephorachallenge.domain.DisplayableProduct
import com.example.sephorachallenge.domain.mapper.DisplayableProductTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

sealed class ProductsDisplayState {
    object Loading : ProductsDisplayState()
    object Error : ProductsDisplayState()
    data class Success(val products: List<DisplayableProduct>) : ProductsDisplayState()
}

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val productsDatabaseRepository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _displayState: MutableLiveData<ProductsDisplayState> by lazy {
        MutableLiveData<ProductsDisplayState>().apply { getProducts() }
    }

    val displayState: LiveData<ProductsDisplayState>
        get() = _displayState

    fun getProducts() = viewModelScope.launch(dispatcher) {
        _displayState.postValue(ProductsDisplayState.Loading)
        val productsList = productsRepository.fetchProducts()
        productsList?.let { products ->
            productsDatabaseRepository.insertAllProducts(products)
            _displayState.postValue(
                ProductsDisplayState.Success(products = products.map { product ->
                    transformer.transformProduct(product)
                })
            )
        } ?: run {
            _displayState.postValue(ProductsDisplayState.Error)
        }
    }
}