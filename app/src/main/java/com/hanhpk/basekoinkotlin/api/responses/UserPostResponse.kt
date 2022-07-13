package com.hanhpk.basekoinkotlin.api.responses

import com.squareup.moshi.Json

data class UserPostResponse(
    @Json(name= "userId")
    val userId:Int,
    @Json(name= "id")
    val id:Int,
    @Json(name= "title")
    val title:String,
)
