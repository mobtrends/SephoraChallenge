package com.example.sephorachallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Product
import com.example.domain.repository.ProductsRepository
import com.example.sephorachallenge.domain.DisplayableProduct
import com.example.sephorachallenge.domain.mapper.DisplayableProductTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import com.example.sephorachallenge.presentation.viewmodels.product.ProductsDisplayState
import com.example.sephorachallenge.presentation.viewmodels.product.ProductsViewModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.then
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductsRepository

    @Mock
    private lateinit var transformer: DisplayableProductTransformer

    @Mock
    private lateinit var repositoryDatabase: ProductsDatabaseRepository

    @Mock
    private lateinit var observer: Observer<ProductsDisplayState>

    private lateinit var viewModel: ProductsViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        val dispatcher = Dispatchers.Unconfined
        viewModel = ProductsViewModel(repository, repositoryDatabase, transformer, dispatcher)
        Dispatchers.setMain(dispatcher)
        viewModel.displayState.observeForever(observer)
    }

    @Test
    fun `getProducts when repository return null present an error`() = runBlocking {
        // Given
        given(repository.fetchProducts()).willReturn(null)

        // When
        viewModel.getProducts()

        // Then
        inOrder(observer) {
            then(observer).should(this).onChanged(ProductsDisplayState.Loading)
            then(observer).should(this).onChanged(ProductsDisplayState.Error)
        }
    }

    @Test
    fun `getProducts when repository return a list of product present success`() = runBlocking {
        // Given
        val product = mock<Product>()
        val displayableProduct = mock<DisplayableProduct>()
        given(repository.fetchProducts()).willReturn(listOf(product))
        given(transformer.transformProduct(product)).willReturn(displayableProduct)

        // When
        viewModel.getProducts()

        // Then
        inOrder(observer) {
            then(observer).should(this).onChanged(ProductsDisplayState.Loading)
            then(repositoryDatabase).should(only()).insertAllProducts(listOf(product))
            then(observer).should(this)
                .onChanged(ProductsDisplayState.Success(listOf(displayableProduct)))
        }
    }
}