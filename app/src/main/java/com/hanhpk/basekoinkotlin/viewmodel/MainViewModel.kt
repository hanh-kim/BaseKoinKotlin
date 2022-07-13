package com.hanhpk.basekoinkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.base.BaseViewModel
import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetUserPost
import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetUserPostPaging
import com.hanhpk.basekoinkotlin.pagingsources.UserPostPagingSource
import com.hanhpk.basekoinkotlin.utils.Constants

class MainViewModel(
    private val userId: Int,
    private val getUserPostCase: GetUserPost,
    private val getUserPostPagingCase: GetUserPostPaging,
) : BaseViewModel() {

    private val _userPostResponse: MutableLiveData<List<UserPostResponse>> = MutableLiveData()
    val userPostLiveData: LiveData<List<UserPostResponse>>
        get() = _userPostResponse

    fun getUserPost(params: Int) {
        getUserPostCase(params = params) {
            it.fold(
                ::handleFailure,
                ::handleDataResponse
            )
        }
    }

    private fun handleDataResponse(respone: List<UserPostResponse>) {
        _userPostResponse.value = respone
    }

    val userPostPaging = Pager(
        PagingConfig(
            pageSize = Constants.DEFAULT_PAGING_SIZE,
            initialLoadSize = Constants.DEFAULT_PAGING_LIST_INITIAL_PAGE_LOAD_SIZE
        )
    ){
        UserPostPagingSource(getUserPostPagingCase)
    }.liveData.cachedIn(this)
}