package com.example.sephorachallenge.domain

import com.example.domain.ImageUrl

data class DisplayableProductDetail(
    val id: Int,
    val brandName: String,
    val productName: String,
    val price: String,
    val description: String,
    val imageUrl: String,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean
)
