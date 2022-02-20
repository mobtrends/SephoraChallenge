package com.example.sephorachallenge.domain.mapper

import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import com.example.sephorachallenge.data.database.entity.ProductEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductEntityTransformerTest {

    private lateinit var transformer: ProductEntityTransformer

    @Before
    fun setUp() {
        transformer = ProductEntityTransformer()
    }

    @Test
    fun `transform Product to Product Entity`() {
        // Given
        val product = Product(
            id = 12345678,
            name = "productName",
            description = "desc",
            price = 1234,
            imageUrl = ImageUrl(small = "smallUrl", large = "LargeUrl"),
            brand = Brand(id = "id", name = "brandName"),
            isProductSet = true,
            isSpecialBrand = true
        )

        val expectedProductEntity = ProductEntity(
            id = 12345678,
            productName = "productName",
            description = "desc",
            price = 1234,
            smallImageUrl = "smallUrl",
            largeImageUrl = "LargeUrl",
            brandId = "id",
            brandName = "brandName",
            isProductSet = true,
            isSpecialBrand = true
        )

        // When
        val productEntity = transformer.transformProductToProductEntity(product)

        // Then
        Assert.assertEquals(expectedProductEntity, productEntity)
    }

    @Test
    fun `transform Product Entity to Product`() {
        // Given
        val productEntity = ProductEntity(
            id = 12345678,
            productName = "productName",
            description = "desc",
            price = 1234,
            smallImageUrl = "smallUrl",
            largeImageUrl = "LargeUrl",
            brandId = "id",
            brandName = "brandName",
            isProductSet = true,
            isSpecialBrand = true
        )

        val expectedProduct = Product(
            id = 12345678,
            name = "productName",
            description = "desc",
            price = 1234,
            imageUrl = ImageUrl(small = "smallUrl", large = "LargeUrl"),
            brand = Brand(id = "id", name = "brandName"),
            isProductSet = true,
            isSpecialBrand = true
        )

        // When
        val product = transformer.transformProductEntityToProduct(productEntity)

        // Then
        Assert.assertEquals(expectedProduct, product)
    }
}