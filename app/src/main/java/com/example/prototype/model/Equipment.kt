package com.example.prototype.model

import com.google.gson.annotations.SerializedName

data class Equipment(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("barcode")
    var barcode: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("comment")
    var comment: String? = null,

    @SerializedName("request")
    var request: List<Request>? = null
)