package com.example.data.repository

import com.example.data.model.JsonProducts
import com.example.data.transformer.JsonProductsTransformer
import com.example.domain.Product
import com.example.domain.repository.ProductsRepository
import retrofit2.http.GET
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApiService,
    private val productsTransformer: JsonProductsTransformer
) : ProductsRepository {

    override suspend fun fetchProducts(): List<Product>? {
        return try {
            val jsonProducts = api.fetchProducts()
            productsTransformer.getProducts(jsonProducts)
        } catch (e: Exception) {
            null
        }
    }
}

interface ProductsApiService {

    @GET("/items.json")
    suspend fun fetchProducts(): List<JsonProducts>
}