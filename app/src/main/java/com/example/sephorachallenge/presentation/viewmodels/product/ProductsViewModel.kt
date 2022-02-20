package com.example.sephorachallenge.presentation

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

    val displayState: MutableLiveData<ProductsDisplayState> by lazy {
        MutableLiveData<ProductsDisplayState>().apply { getProducts() }
    }

    fun getProducts() = viewModelScope.launch(dispatcher) {
        displayState.postValue(ProductsDisplayState.Loading)
        val productsList = productsRepository.fetchProducts()
        productsList?.let { products ->
            productsDatabaseRepository.insertAllProducts(products)
            displayState.postValue(
                ProductsDisplayState.Success(products = products.map { product ->
                    transformer.transformProduct(product)
                })
            )
        } ?: run {
            displayState.postValue(ProductsDisplayState.Error)
        }
    }
}