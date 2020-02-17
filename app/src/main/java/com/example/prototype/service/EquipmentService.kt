package com.example.prototype.service

import com.example.prototype.model.*
import retrofit2.Call
import retrofit2.http.*

interface EquipmentService {
    @GET("equipments/")
    fun getEquipmentAll(): Call<List<Equipment>>

    @GET("equipments/{id}")
    fun getEquipmentById(@Path("id") id: Int): Call<Equipment>

    @GET("equipments")
    fun getEquipmentByBarcode(@Query("barcode") barcode: String): Call<Equipment>

    @POST("equipments/")
    fun postEquipment(@Body equipment: Equipment): Call<Equipment>
}