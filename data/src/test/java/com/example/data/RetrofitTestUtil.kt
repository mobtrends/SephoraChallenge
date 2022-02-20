package com.example.data

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FileUtil {

    fun readFile(fileName: String): String? {
        return FileUtil::class.java.getResource(fileName)?.readText()
    }
}

object RetrofitTestUtil {

    fun buildAdapter(endPoint: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson))
            .build()
    }
}