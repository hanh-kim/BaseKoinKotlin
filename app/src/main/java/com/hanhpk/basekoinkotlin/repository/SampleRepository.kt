package com.hanhpk.basekoinkotlin.repository

import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.api.services.ApiInterface
import com.hanhpk.basekoinkotlin.base.BaseRepository
import com.hanhpk.basekoinkotlin.exception.Failure
import com.hanhpk.basekoinkotlin.interactors.Result
import kotlinx.coroutines.delay

interface SampleRepository {
    suspend fun getUserPost(params: Int): Result<Failure, List<UserPostResponse>>
    suspend fun getUserPostPaging(params: GeneralPagingRequest): Result<Failure, List<UserPostResponse>>

    class SampleRepositoryImpl(private val apiInterface: ApiInterface) : SampleRepository,
        BaseRepository() {
        override suspend fun getUserPost(params: Int): Result<Failure, List<UserPostResponse>> {
            delay(1000L)
            return request(apiInterface.getUserPost(page = params))
        }

        override suspend fun getUserPostPaging(params: GeneralPagingRequest): Result<Failure, List<UserPostResponse>> {
            delay(1000L)
            return request(apiInterface.getUserPostPaging(limit = params.limit, offset = params.offset))
        }
    }
}