package com.hanhpk.basekoinkotlin.base

import android.util.Log
import com.hanhpk.basekoinkotlin.exception.Failure

import com.hanhpk.basekoinkotlin.interactors.Result
import retrofit2.Call
import retrofit2.awaitResponse

abstract class BaseRepository {
    suspend fun <T> request(
        call: Call<T>
    ): Result<Failure, T> {
        return try {
            val response = call.awaitResponse()
            val responseBody = response.body()
            when (response.code()) {
                in 200..299 -> {
                    if (responseBody != null) {
                        Result.Success(responseBody)
                    } else {
                        Result.Error(Failure.ServerError)
                    }
                }
                else -> {
                    Result.Error(Failure.ServerError)
                }
            }
        } catch (exception: Throwable) {
            Log.e("hanhpkkkk", exception.message.toString())
            Result.Error(Failure.NetworkConnection)
        }
    }
}