package com.example.prototype

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("equipment/{barcode}")
    fun getEquipmentbyBarcode(@Query("barcode") id:Int): Response<Equipment>
}