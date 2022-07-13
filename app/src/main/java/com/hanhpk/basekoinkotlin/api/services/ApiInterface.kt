package com.hanhpk.basekoinkotlin.api.services


import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        private const val GET_SAMPLE = "post"
    }

    @GET(GET_SAMPLE)
    fun getUserPost(@Query("page") page:Int): Call<List<UserPostResponse>>

    @GET(GET_SAMPLE)
    fun getUserPostPaging(
        @Query("limit") limit: Int,
        @Query("page") offset: Int,
    ): Call<List<UserPostResponse>>
}