package com.hanhpk.basekoinkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.hanhpk.basekoinkotlin.api.requests.GeneralPagingRequest
import com.hanhpk.basekoinkotlin.api.responses.PhotoResponse
import com.hanhpk.basekoinkotlin.base.BaseViewModel
import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetPhotoPaging
import com.hanhpk.basekoinkotlin.pagingsources.PhotoPagingSource
import com.hanhpk.basekoinkotlin.utils.Constants

class MainViewModel(
    private val userId: Int,
    private val getUserPostPagingCase: GetPhotoPaging,
) : BaseViewModel() {

    private val _photoResponse: MutableLiveData<PhotoResponse> = MutableLiveData()
    val photoLiveData: LiveData<PhotoResponse>
        get() = _photoResponse
    private val _totalCount: MutableLiveData<Int> = MutableLiveData()
    val totalCount: LiveData<Int>
        get() = _totalCount

    init {
        getUserPostPagingCase.onTotalCount ={
            _totalCount.value = it
        }
    }

    fun getPhotos(page: Int=0) {
        getUserPostPagingCase(params = GeneralPagingRequest(Constants.DEFAULT_PAGING_SIZE,page)) {
            it.fold(
                ::handleFailure,
                ::handleDataResponse
            )
        }
    }

    private fun handleDataResponse(respone: PhotoResponse) {
        _photoResponse.value = respone
    }

    val photoPaging = Pager(
        PagingConfig(
            pageSize = Constants.DEFAULT_PAGING_SIZE,
            initialLoadSize = Constants.DEFAULT_PAGING_LIST_INITIAL_PAGE_LOAD_SIZE
        )
    ){
        PhotoPagingSource(getUserPostPagingCase)
    }.liveData.cachedIn(this)
}