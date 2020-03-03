package com.example.prototype.service

import com.example.prototype.model.Equipment
import com.example.prototype.model.User
import com.example.prototype.model.UserLogin
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.*

interface AuthService {
    @POST("auth/signin")
    fun signIn(@Body json: JsonObject): Call<UserLogin>

//    @POST("auth/signin")
//    fun signIn(@Body json: JsonObject): Call<User>
}