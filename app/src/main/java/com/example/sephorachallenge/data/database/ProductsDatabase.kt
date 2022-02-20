package com.example.sephorachallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sephorachallenge.data.database.dao.ProductDao
import com.example.sephorachallenge.data.database.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = ProductsDatabase.VERSION,
    exportSchema = false
)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductDao

    companion object {
        const val VERSION = 1
    }
}