package com.example.prototype

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofilClient {
    private const val baseUrl = "https://bigv-postgres.herokuapp.com/api/"

    private val okHttpClient= OkHttpClient.Builder()
            .addInterceptor { chain ->
        val original = chain.request()

                val requestBuilder = original.newBuilder()
//                    .addHeader("barcode", "")
                    .method(original.method, original.body)

                val request = requestBuilder.build()
                chain.proceed(request)
    }.build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiService::class.java)
    }
}