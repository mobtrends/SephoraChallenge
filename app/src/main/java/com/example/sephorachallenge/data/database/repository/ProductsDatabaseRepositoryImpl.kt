package com.example.sephorachallenge.data.database.repository

import com.example.domain.Product
import com.example.sephorachallenge.data.database.dao.ProductDao
import com.example.sephorachallenge.domain.mapper.ProductEntityTransformer
import com.example.sephorachallenge.domain.repository.ProductsDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsDatabaseRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val transformer: ProductEntityTransformer
) : ProductsDatabaseRepository {

    override fun insertAllProducts(product: List<Product>) {
        val productsEntityList = product.map { transformer.transformProductToProductEntity(it) }
        dao.insertAll(productsEntityList)
    }

    override fun getProductById(id: Int): Flow<Product?> =
        flow {
            emit(dao.getProductFromId(id)?.let { productEntity ->
                transformer.transformProductEntityToProduct(productEntity)
            })
        }
}