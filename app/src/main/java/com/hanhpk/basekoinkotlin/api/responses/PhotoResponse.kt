package com.hanhpk.basekoinkotlin.api.responses

import com.hanhpk.basekoinkotlin.model.Photo

data class PhotoResponse(
    val photos: Photos,
    val stat: String
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)