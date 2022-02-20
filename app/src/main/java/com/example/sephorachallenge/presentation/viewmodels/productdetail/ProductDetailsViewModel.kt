package com.example.sephorachallenge.presentation.viewmodels.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sephorachallenge.domain.DisplayableProductDetails
import com.example.sephorachallenge.domain.mapper.DisplayableProductDetailsTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
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

    val displayState: MutableLiveData<ProductDetailsDisplayState> by lazy {
        MutableLiveData<ProductDetailsDisplayState>().apply { getProductDetails() }
    }

    fun getProductDetails() = viewModelScope.launch(dispatcher) {
        displayState.postValue(ProductDetailsDisplayState.Loading)
        val storedProduct = productsDatabaseRepository.getProductById(productId)
        storedProduct?.let { product ->
            displayState.postValue(
                ProductDetailsDisplayState.Success(transformer.transformProductDetails(product))
            )
        } ?: run {
            displayState.postValue(ProductDetailsDisplayState.Error)
        }
    }
}