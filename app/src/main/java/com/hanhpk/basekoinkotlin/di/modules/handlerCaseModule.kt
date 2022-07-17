package com.hanhpk.basekoinkotlin.di.modules

import com.hanhpk.basekoinkotlin.interactors.handlerCase.GetPhotoPaging
import org.koin.dsl.module

val handlerCaseModule = module {
    single { GetPhotoPaging(get()) }
}