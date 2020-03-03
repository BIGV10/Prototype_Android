package com.example.prototype.model

import com.google.gson.annotations.SerializedName


data class UserLogin (
    @SerializedName("username")
    val username: String,

    @SerializedName("accessToken")
    val accessToken: String
)