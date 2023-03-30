package com.example.data.repository

import com.example.data.model.JsonProducts
import com.example.data.transformer.JsonProductsTransformer
import com.example.domain.Product
import com.example.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    /** ZIP **/
    /*
    override suspend fun fetchNumberAndLetter(): Flow<String> {
        val numbersFlow = flowOf(1, 2, 3).delayEach(1000)
        val lettersFlow = flowOf("A", "B", "C").delayEach(2000)
        numbersFlow.zip(lettersFlow) { number, letter ->
            "$number$letter"
        }
     }

     repository.fetchNumberAndLetter().collect {
        println(it)
     }

     RESULT ->

     1A
     2B
     3C

     */

    /** COMBINE **/
    /*
    override suspend fun fetchNumberAndLetter(): Flow<String> {
        val numbersFlow = flowOf(1, 2, 3).delayEach(1000)
        val lettersFlow = flowOf("A", "B", "C").delayEach(2000)
        numbersFlow.combine(lettersFlow) { number, letter ->
            "$number$letter"
        }
     }

     repository.fetchNumberAndLetter().collect {
        println(it)
     }

     RESULT ->

     1A
     2A
     3A
     3B
     3C

     */
}

interface ProductsApiService {
    @GET("/items.json")
    suspend fun fetchProducts(): List<JsonProducts>
}