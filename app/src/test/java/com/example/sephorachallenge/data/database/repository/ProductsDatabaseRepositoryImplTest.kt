package com.example.sephorachallenge.data.database.repository

import com.example.domain.Product
import com.example.sephorachallenge.data.database.dao.ProductDao
import com.example.sephorachallenge.data.database.entity.ProductEntity
import com.example.sephorachallenge.domain.mapper.ProductEntityTransformer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.then
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsDatabaseRepositoryImplTest {

    @Mock
    private lateinit var dao: ProductDao

    @Mock
    private lateinit var transformer: ProductEntityTransformer

    @InjectMocks
    private lateinit var repository: ProductsDatabaseRepositoryImpl

    @Test
    fun `insertAllProduct insert in DB`() {
        // Given
        val product = mock<Product>()
        val productEntity = mock<ProductEntity>()
        given(transformer.transformProductToProductEntity(product)).willReturn(productEntity)

        // When
        repository.insertAllProducts(listOf(product))

        // Then
        then(dao).should(only()).insertAll(listOf(productEntity))
    }

    @Test
    fun `getProductById get Product with a specific id`() {
        // Given
        val productEntity = mock<ProductEntity>()
        val expectedProduct = mock<Product>()

        given(dao.getProductFromId(2345678)).willReturn(productEntity)
        given(transformer.transformProductEntityToProduct(productEntity)).willReturn(expectedProduct)

        // When
        val product = repository.getProductById(2345678)

        // Then
        assertEquals(expectedProduct, product)
    }

    @Test
    fun `getProductById return null when get Product with a specific id`() {
        // Given
        given(dao.getProductFromId(2345678)).willReturn(null)

        // When
        val product = repository.getProductById(2345678)

        // Then
        assertNull(product)
    }
}