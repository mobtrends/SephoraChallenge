package com.example.sephorachallenge.domain.repository

import com.example.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductsDatabaseRepository {
    fun insertAllProducts(product: List<Product>)
    fun getProductById(id: Int) : Flow<Product?>
}