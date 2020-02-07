package com.example.prototype

import com.google.gson.annotations.SerializedName

data class Equipment(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("barcode")
    var barcode: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("comment")
    var comment: String? = null
)