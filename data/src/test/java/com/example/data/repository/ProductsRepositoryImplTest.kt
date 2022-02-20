package com.example.data.repository

import com.example.data.FileUtil
import com.example.data.RetrofitTestUtil
import com.example.data.transformer.JsonProductsTransformer
import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProductsRepositoryImplTest {

    private val server = MockWebServer()
    private lateinit var repository: ProductsRepositoryImpl

    @Before
    fun setUp() {
        server.start()
        val adapter = RetrofitTestUtil.buildAdapter(server.url("/"))
        val service = adapter.create(ProductsApiService::class.java)

        repository = ProductsRepositoryImpl(service, JsonProductsTransformer())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `fetchProduct integration test`() = runBlocking {
        // Given
        server.enqueue(MockResponse().setBody(FileUtil.readFile("/json/products.json") ?: ""))
        val expectedProduct = listOf(
            Product(
                id = 1461267310,
                name = "Size Up",
                description = "Description",
                price = 140,
                imageUrl = ImageUrl(small = "https://link.fr", large = ""),
                brand = Brand(id = "SEPHO", name = "SEPHORA COLLECTION"),
                isProductSet = false,
                isSpecialBrand = false
            )
        )

        // When
        val products = repository.fetchProducts()

        // Then
        Assert.assertNotNull(products)
        Assert.assertEquals(
            expectedProduct, products
        )
    }

    @Test
    fun `fetchProduct integration test on response code != 200`() = runBlocking {
        // Given
        val response = MockResponse()
        response.setResponseCode(404)
        server.enqueue(response)

        // When
        val products = repository.fetchProducts()

        // Then
        Assert.assertNull(products)
    }

    @Test
    fun `fetchProduct integration test on error`() = runBlocking {
        // Given
        val response = MockResponse()
        response.socketPolicy = SocketPolicy.DISCONNECT_AT_START
        server.enqueue(response)

        // When
        val products = repository.fetchProducts()

        // Then
        Assert.assertNull(products)
    }
}