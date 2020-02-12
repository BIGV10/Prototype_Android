package com.example.prototype.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("comment")
    var comment: String? = null,
    @SerializedName("status")
    var status: Int? = null,
    @SerializedName("equipment")
    var equipment: List<Equipment>? = null
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