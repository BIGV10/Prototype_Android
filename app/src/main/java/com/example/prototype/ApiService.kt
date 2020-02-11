package com.example.prototype

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import kotlinx.coroutines.*
import okhttp3.Interceptor
import com.example.prototype.Equipment.*

interface ApiService {
    @GET("equipment/{barcode}")
    fun getEquipmentbyBarcode(): Call<Equipment>



//    @GET("equipment/{barcode}")
//    fun getEquipmentbyBarcode(
//        @Query("barcode") barcode:String)
//            :Deferred<Equipment>
//
//    companion object{
//        operator fun invoke(): ApiService {
//            val requestInterceptor = Interceptor { chain ->
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .build()
//
//                val request = chain.request()
//                    .newBuilder()
//                    .build()
//
//
//
//
//
//
//
//            }
//        }
//    }
}