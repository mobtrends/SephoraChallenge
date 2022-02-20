package com.example.sephorachallenge.presentation.viewmodels.productdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Product
import com.example.sephorachallenge.domain.DisplayableProductDetail
import com.example.sephorachallenge.domain.mapper.DisplayableProductDetailTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductsDatabaseRepository

    @Mock
    private lateinit var transformer: DisplayableProductDetailTransformer

    @Mock
    private lateinit var observer: Observer<ProductDetailDisplayState>

    private lateinit var viewModel: ProductDetailViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        val dispatcher = Dispatchers.Unconfined
        viewModel = ProductDetailViewModel(
            PRODUCT_ID,
            repository,
            transformer,
            dispatcher
        )
        Dispatchers.setMain(dispatcher)
        viewModel.displayState.observeForever(observer)
    }

    @Test
    fun `getProductDetails when repository return null present an error`() {
        // Given
        given(repository.getProductById(PRODUCT_ID)).willReturn(null)

        // When
        viewModel.getProductDetails()

        // Then
        inOrder(observer) {
            then(observer).should(this).onChanged(ProductDetailDisplayState.Loading)
            then(observer).should(this).onChanged(ProductDetailDisplayState.Error)
        }
    }

    @Test
    fun `getProductDetails when repository return a product present success`() {
        // Given
        val product = mock<Product>()
        val displayableProductDetail = mock<DisplayableProductDetail>()
        given(repository.getProductById(PRODUCT_ID)).willReturn(product)
        given(transformer.transformProductDetail(product)).willReturn(displayableProductDetail)

        // When
        viewModel.getProductDetails()

        // Then
        inOrder(observer) {
            then(observer).should(this).onChanged(ProductDetailDisplayState.Loading)
            then(observer).should(this)
                .onChanged(ProductDetailDisplayState.Success(displayableProductDetail))
        }
    }

    companion object {
        private const val PRODUCT_ID = 112345667
    }
}