package com.hanhpk.basekoinkotlin.repository

import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.api.responses.PhotoResponse
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.api.services.ApiInterface
import com.hanhpk.basekoinkotlin.base.BaseRepository
import com.hanhpk.basekoinkotlin.exception.Failure
import com.hanhpk.basekoinkotlin.interactors.Result
import kotlinx.coroutines.delay

interface SampleRepository {
    suspend fun getPhotoPaging(params: GeneralPagingRequest): Result<Failure, PhotoResponse>

    class SampleRepositoryImpl(private val apiInterface: ApiInterface) : SampleRepository,
        BaseRepository() {

        override suspend fun getPhotoPaging(params: GeneralPagingRequest): Result<Failure, PhotoResponse> {
            delay(1000L)
            return request(apiInterface.getPhotoPaging(perPage = params.perPage, page = params.page))
        }
    }
}