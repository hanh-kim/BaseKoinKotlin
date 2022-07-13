package com.hanhpk.basekoinkotlin.interactors.handlerCase

import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.exception.Failure
import com.hanhpk.basekoinkotlin.interactors.HandlerCase
import com.hanhpk.basekoinkotlin.interactors.Result
import com.hanhpk.basekoinkotlin.repository.SampleRepository

class GetUserPost(private val sampleRepository: SampleRepository) :
    HandlerCase<List<UserPostResponse>, Int>() {
    override suspend fun run(params: Int): Result<Failure, List<UserPostResponse>> {
        return sampleRepository.getUserPost(params)
    }
}