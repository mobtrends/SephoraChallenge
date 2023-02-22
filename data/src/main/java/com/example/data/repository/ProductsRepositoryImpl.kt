package com.example.data.repository

import com.example.data.model.JsonProducts
import com.example.data.transformer.JsonProductsTransformer
import com.example.domain.Product
import com.example.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsApiService,
    private val productsTransformer: JsonProductsTransformer
) : ProductsRepository {

    override suspend fun fetchProducts(): Flow<List<Product>?> {
        return flow {
            try {
                val jsonProducts = api.fetchProducts()
                emit(productsTransformer.getProducts(jsonProducts))
            } catch (e: Exception) {
                emit(null)
            }
        }
    }
}

interface ProductsApiService {
    @GET("/items.json")
    suspend fun fetchProducts(): List<JsonProducts>
}