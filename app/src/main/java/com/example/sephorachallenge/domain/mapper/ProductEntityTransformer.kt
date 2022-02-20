package com.example.sephorachallenge.domain.mapper

import com.example.domain.Brand
import com.example.domain.ImageUrl
import com.example.domain.Product
import com.example.sephorachallenge.data.database.entity.ProductEntity
import javax.inject.Inject

class ProductEntityTransformer @Inject constructor() {

    fun transformProductToProductEntity(product: Product) = ProductEntity(
        id = product.id,
        productName = product.name,
        description = product.description,
        price = product.price,
        smallImageUrl = product.imageUrl.small,
        largeImageUrl = product.imageUrl.large,
        brandId = product.brand.id,
        brandName = product.brand.name,
        isProductSet = product.isProductSet,
        isSpecialBrand = product.isSpecialBrand
    )

    fun transformProductEntityToProduct(productEntity: ProductEntity): Product? = Product(
        id = productEntity.id,
        name = productEntity.productName,
        description = productEntity.description,
        price = productEntity.price,
        imageUrl = ImageUrl(productEntity.smallImageUrl, productEntity.largeImageUrl),
        brand = Brand(productEntity.brandId, productEntity.brandName),
        isProductSet = productEntity.isProductSet,
        isSpecialBrand = productEntity.isSpecialBrand
    )
}