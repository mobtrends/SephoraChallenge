package com.example.data.transformer

import com.example.data.model.JsonProducts
import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import javax.inject.Inject

class JsonProductsTransformer @Inject constructor() {

    fun getProducts(jsonProducts: List<JsonProducts>): List<Product>? =
        jsonProducts.map { jsonItem ->
            Product(
                jsonItem.productId,
                jsonItem.productName,
                jsonItem.description,
                jsonItem.price,
                ImageUrl(jsonItem.imageUrl.small, jsonItem.imageUrl.large),
                Brand(jsonItem.cBrand.id, jsonItem.cBrand.name),
                jsonItem.isProductSet,
                jsonItem.isProductBrand
            )
        }.ifEmpty { null }
}