package com.hanhpk.basekoinkotlin.interactors.handlerCase

import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.exception.Failure
import com.hanhpk.basekoinkotlin.interactors.HandlerCase
import com.hanhpk.basekoinkotlin.interactors.Result
import com.hanhpk.basekoinkotlin.repository.SampleRepository

class GetUserPostPaging(private val sampleRepository: SampleRepository) :
    HandlerCase<List<UserPostResponse>, GeneralPagingRequest>() {
    override suspend fun run(params: GeneralPagingRequest): Result<Failure, List<UserPostResponse>> {
        return sampleRepository.getUserPostPaging(params).fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                Result.Error(it)
            }
        )
    }
}