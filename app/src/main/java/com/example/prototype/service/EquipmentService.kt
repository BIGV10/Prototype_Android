package com.example.prototype.service

import com.example.prototype.Equipment
import retrofit2.Call
import retrofit2.http.GET

class EquipmentService {
    @GET("equipment/{id}")
    fun getEquipmentById(id: Long): Call<Equipment> {
    }
}