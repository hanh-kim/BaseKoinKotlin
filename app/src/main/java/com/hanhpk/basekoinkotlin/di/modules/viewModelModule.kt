package com.hanhpk.basekoinkotlin.di.modules

import com.hanhpk.basekoinkotlin.viewmodel.MainActivityViewModel
import com.hanhpk.basekoinkotlin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { (id:Int)-> MainViewModel(id, get()) }
}