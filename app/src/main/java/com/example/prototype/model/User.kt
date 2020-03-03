package com.example.prototype.model

import com.google.gson.annotations.SerializedName

// Не используется
data class User(
    @SerializedName("id")
    val id: Long? = 0,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("first_name")
    var firstName: String? = null,

    @SerializedName("last_name")
    var lastName: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("enabled")
    var enabled: Boolean = false,

    @SerializedName("roles")
    var roles: List<Role>? = null
)