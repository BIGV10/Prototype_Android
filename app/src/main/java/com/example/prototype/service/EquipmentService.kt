package com.example.prototype.service

import com.example.prototype.model.Equipment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EquipmentService {
    @GET("equipment/")
    fun getEquipmentAll(): Call<List<Equipment>>

    @GET("equipment/{id}")
    fun getEquipmentById(@Path("id") id: Int): Call<Equipment>

    @GET("equipment/")
    fun getEquipmentByBarcode(@Query("barcode") barcode: String): Call<Equipment>
}