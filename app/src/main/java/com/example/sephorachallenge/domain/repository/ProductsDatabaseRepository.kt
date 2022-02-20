package com.example.sephorachallenge.domain.repository

import com.example.domain.Product

interface ProductsDatabaseRepository {
    fun insertAllProducts(product: List<Product>)
    fun getProductById(id: Int) : Product?
}