package com.example.domain

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: ImageUrl,
    val brand: Brand,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean
)

data class ImageUrl(val small: String, val large: String)

data class Brand(val id: String, val name: String)