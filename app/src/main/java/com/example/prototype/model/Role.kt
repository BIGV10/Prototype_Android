package com.example.prototype.model

import com.google.gson.annotations.SerializedName

// Не используется
data class Role(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String
)