package com.example.sephorachallenge.presentation.viewmodels.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.ProductsRepository
import com.example.sephorachallenge.domain.DisplayableProduct
import com.example.sephorachallenge.domain.mapper.DisplayableProductTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val productsDatabaseRepository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _displayState: MutableSharedFlow<ProductsDisplayState> by lazy {
        MutableSharedFlow()
    }

    val displayState: SharedFlow<ProductsDisplayState>
        get() = _displayState.asSharedFlow()

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

        productsRepository.fetchProducts()
            .catch { _displayState.emit(ProductsDisplayState.Error) }
            .collect { products ->
                products?.let {
                    productsDatabaseRepository.insertAllProducts(products)
                    _displayState.emit(
                        ProductsDisplayState.Success(products = products.map { product ->
                            transformer.transformProduct(product)
                        })
                    )
                }
            }
    }
}

sealed class ProductsDisplayState {
    object Loading : ProductsDisplayState()
    object Error : ProductsDisplayState()
    data class Success(val products: List<DisplayableProduct>) : ProductsDisplayState()
}