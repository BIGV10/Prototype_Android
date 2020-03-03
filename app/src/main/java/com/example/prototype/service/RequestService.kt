package com.example.prototype.service

import com.example.prototype.model.*
import retrofit2.Call
import retrofit2.http.*

interface RequestService {
    @POST("requests/")
    fun postRequest(@Body newRequest: Request): Call<Request>

    @POST("requests/{requestId}/equipment/{equipmentId}")
    fun postEquipmentToRequest(
        @Path("requestId") requestId: Int,
        @Path("equipmentId") equipmentId: Int
//        @Header("Authorization") token: String
    ): Call<Equipment>

    @GET("requests/last")
    fun getLastRequests(@Header("Authorization") token: String): Call<List<Request>>
}