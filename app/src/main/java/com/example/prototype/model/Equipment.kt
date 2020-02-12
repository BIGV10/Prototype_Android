package com.example.prototype.model

import com.google.gson.annotations.SerializedName

data class Equipment(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("barcode")
    val barcode: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("request")
    val request: List<Request>? = null
)

//data class Equipment(
//    @SerializedName("id")
//    val id: Long? = null,
//    @SerializedName("barcode")
//    var barcode: String? = null,
//    @SerializedName("name")
//    var name: String? = null,
//    @SerializedName("comment")
//    var comment: String? = null,
//    @SerializedName("request")
//    var request: MutableSet<Request> = HashSet()
//)