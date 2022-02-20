package com.example.data

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    private val LOG_TAG = RetrofitUtil::class.java.simpleName
    private const val CACHE_SIZE_DEFAULT = 5 * 1024 * 1024 // 5 Mb

    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }

    fun buildRestAdapter(
        context: Context,
        endPoint: String,
        connectionTimeout: Int,
        socketTimeout: Int,
        cacheEnabled: Boolean = true
    ): Retrofit {
        val timeoutClient = getTimeOutedClient(
            context,
            connectionTimeout,
            socketTimeout,
            cacheEnabled,
            loggingInterceptor
        )

        return Retrofit.Builder()
            .baseUrl(endPoint)
            .client(timeoutClient)
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson))
            .build()
    }

    private fun getTimeOutedClient(
        context: Context,
        connectionTimeout: Int,
        socketTimeout: Int,
        cacheEnabled: Boolean,
        httpLoggingInterceptor: HttpLoggingInterceptor?
    ): OkHttpClient {
        var cache: Cache? = null

        if (cacheEnabled) {
            try {
                val cacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
                cache = Cache(cacheDirectory, CACHE_SIZE_DEFAULT.toLong())
            } catch (ex: Exception) {
                Log.w(LOG_TAG, "Error when configuring cache for okhttp : ", ex)
            }
        }

        val builder = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(connectionTimeout.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(socketTimeout.toLong(), TimeUnit.MILLISECONDS)

        if (httpLoggingInterceptor != null) {
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder.build()
    }
}