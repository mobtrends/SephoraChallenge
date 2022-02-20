package com.example.sephorachallenge.domain.mapper

import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DisplayableProductTransformerTest {

    private lateinit var transformer: DisplayableProductTransformer

    @Before
    fun setUp() {
        transformer = DisplayableProductTransformer()
    }

    @Test
    fun `transformProduct with a product`() {
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

        // When
        val displayableProduct = transformer.transformProduct(product)

        // Then
        assertEquals(12345678, displayableProduct.id)
        assertEquals("brandName", displayableProduct.brandName)
        assertEquals("smallUrl", displayableProduct.image)
        assertEquals("1234 €", displayableProduct.price)
        assertEquals("productName", displayableProduct.productName)
    }
}