package com.example.domain.repository

import com.example.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun fetchProducts(): Flow<List<Product>?>
}
