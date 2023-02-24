package com.example.sephorachallenge.presentation.viewmodels.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.ProductsRepository
import com.example.sephorachallenge.domain.DisplayableProduct
import com.example.sephorachallenge.domain.mapper.DisplayableProductTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val productsDatabaseRepository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _displayState: MutableStateFlow<ProductsDisplayState> by lazy {
        MutableStateFlow(ProductsDisplayState.Loading)
    }

    val displayState: StateFlow<ProductsDisplayState>
        get() = _displayState.asStateFlow()

    fun getProducts() = viewModelScope.launch(dispatcher) {

        /** SEQUENTIAL CALLS **/
        /*

        val productFlow = async { productsRepository.fetchProducts() }
        val anotherThingFlow = async { anotherRepository.fetchAnotherThing() }
        val anotherThing2Flow = async { anotherRepository.fetchAnotherThing2() }

        val product = productFlow.await()
        val anotherThing = anotherThingFlow.await()
        val anotherThing2 = anotherThing2Flow.await()

        product.collect { prod ->
            anotherThing.collect { at ->
                anotherThing2.collect { at2 ->
                    ....
                    something = transform.productAndAnotherThing(prod, at, at2)
                    displayState.value = SomethingDisplayState.Success(something)
                }
            }
        }
        */

        productsRepository.fetchProducts().collect { products ->
            products?.let {
                productsDatabaseRepository.insertAllProducts(products)
                _displayState.value =
                    ProductsDisplayState.Success(products = products.map { product ->
                        transformer.transformProduct(product)
                    })
            } ?: run {
                _displayState.value = ProductsDisplayState.Error
            }
        }
    }
}

sealed class ProductsDisplayState {
    object Loading : ProductsDisplayState()
    object Error : ProductsDisplayState()
    data class Success(val products: List<DisplayableProduct>) : ProductsDisplayState()
}