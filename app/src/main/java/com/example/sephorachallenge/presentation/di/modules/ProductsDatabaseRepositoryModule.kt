package com.example.sephorachallenge.presentation.di.modules

import android.content.Context
import androidx.room.Room
import com.example.sephorachallenge.data.database.ProductsDatabase
import com.example.sephorachallenge.data.database.dao.ProductDao
import com.example.sephorachallenge.domain.mapper.ProductEntityTransformer
import dagger.Module
import dagger.Provides

@Module
class ProductsDatabaseRepositoryModule {

    @Provides
    fun provideDatabase(context: Context): ProductsDatabase =
        Room.databaseBuilder(context, ProductsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideProductEntityTranformer() =
        ProductEntityTransformer()

    @Provides
    fun provideProductDao(db: ProductsDatabase): ProductDao {
        return db.productsDao()
    }

    companion object {
        private const val DATABASE_NAME = "ProductsDatabase"
    }
}