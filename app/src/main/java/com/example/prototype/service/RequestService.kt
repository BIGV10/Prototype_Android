package com.example.prototype.service

import com.example.prototype.model.*
import retrofit2.Call
import retrofit2.http.*

interface RequestService {
    @POST("request/")
    fun postRequest(@Body newRequest: Request): Call<Request>

    @POST("request/{requestId}/equipment/{equipmentId}")
    fun postEquipmentToRequest(@Path("requestId") requestId: Int, @Path("equipmentId") equipmentId: Int): Call<Equipment>

    @GET("lastRequests/")
    fun getLastRequests(): Call<List<Request>>
}