package com.example.sephorachallenge.domain.mapper

import com.example.domain.Product
import com.example.sephorachallenge.domain.DisplayableProductDetails
import com.example.sephorachallenge.domain.mapper.utils.PriceUtil
import javax.inject.Inject

class DisplayableProductDetailsTransformer @Inject constructor() {
    fun transformProductDetails(product: Product) =
        DisplayableProductDetails(
            id = product.id,
            brandName = product.brand.name,
            productName = product.name,
            price = PriceUtil.formatPrice(product.price),
            description = product.description,
            imageUrl = product.imageUrl.small,
            isProductSet = product.isProductSet,
            isSpecialBrand = product.isSpecialBrand
        )
}