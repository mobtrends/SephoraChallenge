package com.example.domain.repository

import com.example.domain.Product

interface ProductsRepository {
    suspend fun fetchProducts(): List<Product>?
}
