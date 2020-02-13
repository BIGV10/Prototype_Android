package com.example.prototype.service

import com.example.prototype.model.*
import retrofit2.Call
import retrofit2.http.*

interface EquipmentService {
    @GET("equipment/")
    fun getEquipmentAll(): Call<List<Equipment>>

    @GET("equipment/{id}")
    fun getEquipmentById(@Path("id") id: Int): Call<Equipment>

    @GET("equipment/")
    fun getEquipmentByBarcode(@Query("barcode") barcode: String): Call<Equipment>

    @POST("equipment/")
    fun postEquipment(@Body equipment: Equipment): Call<Equipment>
}