package com.example.prototype.service

import com.example.prototype.model.Equipment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EquipmentService {
    @GET("equipment/{id}")
    fun getEquipmentById(@Path("id") id: Long): Call<Equipment>

    @GET("equipment")
    fun getAllEquipment(): Call<List<Equipment>>
}