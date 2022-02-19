package com.example.sephorachallenge.domain.mapper

import com.example.domain.Product
import com.example.sephorachallenge.domain.DisplayableProduct
import javax.inject.Inject

class DisplayableProductTransformer @Inject constructor() {
    fun transformProduct(product: Product): DisplayableProduct {
        return DisplayableProduct(
            brandName = product.brand.name,
            productName = product.name,
            image = product.imageUrl.small,
            price = formatPrice(product.price),
            id = product.id
        )
    }

    private fun formatPrice(price: Int) = "$price €"
}