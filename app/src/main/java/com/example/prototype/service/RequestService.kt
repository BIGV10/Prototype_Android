package com.example.prototype.service

import com.example.prototype.model.Equipment
import com.example.prototype.model.Request
import retrofit2.Call
import retrofit2.http.*

interface RequestService {
    @POST("requests/")
    fun postRequest(
        @Body newRequest: Request,
        @Header("Authorization") token: String
    ): Call<Request>

    @POST("requests/{requestId}/equipment/{equipmentId}")
    fun postEquipmentToRequest(
        @Path("requestId") requestId: Int,
        @Path("equipmentId") equipmentId: Int,
        @Header("Authorization") token: String
    ): Call<Equipment>

    @GET("requests/last")
    fun getLastRequests(
        @Header("Authorization") token: String
    ): Call<List<Request>>

    @GET("requests/user")
    fun getUserRequests(
        @Header("Authorization") token: String
    ): Call<List<Request>>
}