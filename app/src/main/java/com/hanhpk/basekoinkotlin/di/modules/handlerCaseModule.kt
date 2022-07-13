package com.hanhpk.basekoinkotlin.di.modules

import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetUserPost
import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetUserPostPaging
import com.hanhpk.basekoinkotlin.repository.SampleRepository
import org.koin.dsl.module

val handlerCaseModule = module {
    single { GetUserPost(get()) }
    single { GetUserPostPaging(get()) }
}