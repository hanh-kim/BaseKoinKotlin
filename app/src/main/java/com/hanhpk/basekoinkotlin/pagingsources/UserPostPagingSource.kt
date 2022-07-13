package com.hanhpk.basekoinkotlin.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetUserPostPaging

class UserPostPagingSource(
    private val handlerCase: GetUserPostPaging
): PagingSource<Int, UserPostResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPostResponse> {
        val currentKey = params.key ?: 0
        return handlerCase.run(GeneralPagingRequest(params.loadSize, currentKey)).fold(
            onFailure =  {
                LoadResult.Error(it)
            },
            onSuccess =  {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = (currentKey + it.size).takeIf { _ -> it.size == params.loadSize }
                )
            }
        )
    }
    override fun getRefreshKey(state: PagingState<Int, UserPostResponse>): Int? = null
}