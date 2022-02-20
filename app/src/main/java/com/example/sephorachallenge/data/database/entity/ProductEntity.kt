package com.example.sephorachallenge.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductEntity")
data class ProductEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo var productName: String,
    @ColumnInfo var description: String,
    @ColumnInfo var price: Int,
    @ColumnInfo var smallImageUrl: String,
    @ColumnInfo var largeImageUrl: String,
    @ColumnInfo var brandId: String,
    @ColumnInfo var brandName: String,
    @ColumnInfo var isProductSet: Boolean,
    @ColumnInfo var isSpecialBrand: Boolean,
)
