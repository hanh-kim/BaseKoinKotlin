package com.hanhpk.basekoinkotlin.api.services


import com.hanhpk.basekoinkotlin.api.responses.PhotoResponse
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        private const val GET_PHOTO = "?method=flickr.favorites.getList&api_key=c80727edc0b98577bf0989a24613ad08&user_id=191864893%40N06&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&format=json&nojsoncallback=1"
    }

    @GET(GET_PHOTO)
    fun getPhotoPaging(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): Call<PhotoResponse>
}