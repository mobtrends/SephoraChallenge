package com.example.data.transformer

import com.example.data.model.JsonBrand
import com.example.data.model.JsonImageUrl
import com.example.data.model.JsonProducts
import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class JsonProductsTransformerTest {

    private val transformer = JsonProductsTransformer()

    @Test
    fun `getProducts when products items are empty`() {
        // When
        val productsList = transformer.getProducts(emptyList())

        // Then
        assertNull(productsList)
    }

    @Test
    fun `getProducts when products items are not empty`() {
        // Given
        val jsonProducts = listOf(
            JsonProducts(
                productId = 1234567,
                productName = "Name",
                description = "Description",
                price = 1000,
                imageUrl = JsonImageUrl(small = "url", large = "url"),
                cBrand = JsonBrand(id = "id", name = "name"),
                isProductSet = true,
                isProductBrand = true
            )
        )

        val expectedProducts = listOf(
            Product(
                id = 1234567,
                name = "Name",
                description = "Description",
                price = 1000,
                imageUrl = ImageUrl(small = "url", large = "url"),
                brand = Brand(id = "id", name = "name"),
                isProductSet = true,
                isSpecialBrand = true
            )
        )

        // When
        val productsList = transformer.getProducts(jsonProducts)

        // Then
        assertEquals(expectedProducts, productsList)
    }
}