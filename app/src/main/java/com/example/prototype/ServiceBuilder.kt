package com.example.prototype

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    private const val BASE_URL = "http://bigv.ddns.net:9000/api/"
//    private const val BASE_URL = "https://bigv-prototype.herokuapp.com/api/"
//    private const val BASE_URL = "http://localhost:8080/api/"
//    private const val BASE_URL = "http://10.0.2.2:8080/api/" //localhost of PC

//    var client = OkHttpClient().newBuilder()
//        .build()
//    var mediaType: MediaType = MediaType.parse("application/json")
//    var body: RequestBody =
//        create(mediaType, "{\n    \"username\": \"admin\",\n    \"password\": \"123\"\n}")
//    var request: Request = Builder()
//        .url("http://bigv.ddns.net:9000/api/auth/signin")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .build()
//    var response: Response = client.newCall(request).execute()

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}