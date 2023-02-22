package com.example.sephorachallenge.presentation.viewmodels.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sephorachallenge.domain.DisplayableProductDetails
import com.example.sephorachallenge.domain.mapper.DisplayableProductDetailsTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ProductDetailsDisplayState {
    object Loading : ProductDetailsDisplayState()
    object Error : ProductDetailsDisplayState()
    data class Success(val displayableProductDetails: DisplayableProductDetails) :
        ProductDetailsDisplayState()
}

class ProductDetailsViewModel(
    private val productId: Int,
    private val productsDatabaseRepository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductDetailsTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _displayState: MutableStateFlow<ProductDetailsDisplayState> by lazy {
        MutableStateFlow(ProductDetailsDisplayState.Loading)
    }

    val displayState: StateFlow<ProductDetailsDisplayState>
        get() = _displayState

    fun getProductDetails() = viewModelScope.launch(dispatcher) {
        productsDatabaseRepository.getProductById(productId).collect { product ->
            product?.let {
                _displayState.value =
                    ProductDetailsDisplayState.Success(transformer.transformProductDetails(product))
            } ?: run {
                _displayState.value = ProductDetailsDisplayState.Error
            }
        }
    }
}