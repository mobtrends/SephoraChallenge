package com.example.sephorachallenge.domain.mapper

import com.example.domain.Product
import com.example.sephorachallenge.domain.DisplayableProduct
import javax.inject.Inject

class DisplayableProductTransformer @Inject constructor() {
    fun transformProduct(product: Product): DisplayableProduct {
        return DisplayableProduct(
            product.brand.name,
            product.name,
            product.imageUrl.small,
            formatPrice(product.price),
            product.id
        )
    }

    private fun formatPrice(price: Int) = "$price €"
}