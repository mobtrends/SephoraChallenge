package com.example.sephorachallenge.domain.mapper

import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisplayableProductDetailTransformerTest {

    private lateinit var transformer: DisplayableProductDetailTransformer

    @Before
    fun setUp() {
        transformer = DisplayableProductDetailTransformer()
    }

    @Test
    fun `transformProductDetail transform product to a product detail`() {
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
        val displayableProductDetail = transformer.transformProductDetail(product)

        // Then
        Assert.assertEquals(12345678, displayableProductDetail.id)
        Assert.assertEquals("productName", displayableProductDetail.productName)
        Assert.assertEquals("brandName", displayableProductDetail.brandName)
        Assert.assertEquals("1234 €", displayableProductDetail.price)
        Assert.assertEquals("desc", displayableProductDetail.description)
        Assert.assertEquals("smallUrl", displayableProductDetail.imageUrl)
        Assert.assertEquals(true, displayableProductDetail.isProductSet)
        Assert.assertEquals(true, displayableProductDetail.isSpecialBrand)
    }
}