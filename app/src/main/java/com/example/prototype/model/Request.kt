package com.example.prototype.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("equipment")
    val equipment: List<Equipment>? = null
)

//data class Request (
//    @SerializedName("id")
//    val id: Long? = null,
//    @SerializedName("comment")
//    var comment: String? = null,
//    @SerializedName("status")
//    var status: Long? = null,
//    @SerializedName("equipment")
//    var equipment: MutableSet<Equipment> = HashSet()
//)