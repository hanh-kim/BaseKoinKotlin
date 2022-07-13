package com.hanhpk.basekoinkotlin.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val failure:MutableLiveData<Throwable> = MutableLiveData()

    fun handleFailure(throwable: Throwable){
        failure.value = throwable
    }
}