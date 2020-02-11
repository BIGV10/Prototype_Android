package com.example.prototype

import com.example.prototype.model.Equipment
import retrofit2.Call
import retrofit2.http.*

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