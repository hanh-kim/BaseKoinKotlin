package com.hanhpk.basekoinkotlin.interactors.handlerCase

import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.api.responses.PhotoResponse
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.exception.Failure
import com.hanhpk.basekoinkotlin.interactors.HandlerCase
import com.hanhpk.basekoinkotlin.interactors.Result
import com.hanhpk.basekoinkotlin.repository.SampleRepository

class GetPhotoPaging(private val sampleRepository: SampleRepository) :
    HandlerCase<PhotoResponse, GeneralPagingRequest>() {
    var onTotalCount:(Int)->Unit={}
    override suspend fun run(params: GeneralPagingRequest): Result<Failure, PhotoResponse> {
        return sampleRepository.getPhotoPaging(params).fold(
            onSuccess = {
                onTotalCount(it.photos.total)
                Result.Success(it)
            },
            onFailure = {
                Result.Error(it)
            }
        )
    }
}