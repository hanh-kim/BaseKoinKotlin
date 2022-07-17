package com.hanhpk.basekoinkotlin.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetPhotoPaging
import com.hanhpk.basekoinkotlin.model.Photo

class PhotoPagingSource(
    private val handlerCase: GetPhotoPaging
): PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentKey = params.key ?: 0
        return handlerCase.run(GeneralPagingRequest(params.loadSize, currentKey)).fold(
            onFailure =  {
                LoadResult.Error(it)
            },
            onSuccess =  {
                LoadResult.Page(
                    data = it.photos.photo,
                    prevKey = null,
                    nextKey = (currentKey + 1).takeIf { _ -> it.photos.photo.size == params.loadSize }
                )
            }
        )
    }
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = null
}