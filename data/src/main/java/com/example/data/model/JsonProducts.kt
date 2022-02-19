package com.example.data.model

import com.google.gson.annotations.SerializedName

data class JsonProducts(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("product_name") val productName: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("images_url") val imageUrl: JsonImageUrl,
    @SerializedName("c_brand") val cBrand: JsonBrand,
    @SerializedName("is_productSet") val isProductSet: Boolean,
    @SerializedName("is_special_brand") val isProductBrand: Boolean,
)

data class JsonImageUrl(
    @SerializedName("small") val small: String,
    @SerializedName("large") val large: String
)

data class JsonBrand(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
