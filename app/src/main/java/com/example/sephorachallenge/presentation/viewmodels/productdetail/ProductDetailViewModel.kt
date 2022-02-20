package com.example.sephorachallenge.presentation.viewmodels.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sephorachallenge.domain.DisplayableProductDetail
import com.example.sephorachallenge.domain.mapper.DisplayableProductDetailTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

sealed class ProductDetailDisplayState {
    object Loading : ProductDetailDisplayState()
    object Error : ProductDetailDisplayState()
    data class Success(val products: DisplayableProductDetail) : ProductDetailDisplayState()
}

class ProductDetailViewModel(
    private val productId: Int,
    private val productsDatabaseRepository: ProductsDatabaseRepository,
    private val transformer: DisplayableProductDetailTransformer,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val displayState: MutableLiveData<ProductDetailDisplayState> by lazy {
        MutableLiveData<ProductDetailDisplayState>().apply { getProductDetail() }
    }

    fun getProductDetail() = viewModelScope.launch(dispatcher) {
        displayState.postValue(ProductDetailDisplayState.Loading)
        val storedProduct = productsDatabaseRepository.getProductById(productId)
        storedProduct?.let { product ->
            displayState.postValue(ProductDetailDisplayState.Success(transformer.transformProductDetail(product)))
        } ?: kotlin.run {
            displayState.postValue(ProductDetailDisplayState.Error)
        }
    }
}