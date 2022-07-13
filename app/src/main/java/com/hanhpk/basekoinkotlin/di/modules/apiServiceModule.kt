package com.hanhpk.basekoinkotlin.di.modules

import com.hanhpk.basekoinkotlin.api.services.ApiInterface
import org.koin.dsl.module
import retrofit2.Retrofit

val apiServiceModule = module{
    single(createdAtStart = false) { get<Retrofit>().create(ApiInterface::class.java) }
}