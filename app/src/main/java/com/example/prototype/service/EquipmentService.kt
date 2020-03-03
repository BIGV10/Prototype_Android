package com.example.prototype.service

import com.example.prototype.model.*
import retrofit2.Call
import retrofit2.http.*

interface EquipmentService {
    @GET("equipments/")
    fun getEquipmentAll(
        @Header("Authorization") token: String
    ): Call<List<Equipment>>

    @GET("equipments/{id}")
    fun getEquipmentById(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<Equipment>

    @GET("equipments")
    fun getEquipmentByBarcode(
        @Query("barcode") barcode: String,
        @Header("Authorization") token: String
    ): Call<Equipment>

    @POST("equipments/")
    fun postEquipment(
        @Body equipment: Equipment,
        @Header("Authorization") token: String
    ): Call<Equipment>
}