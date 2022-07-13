package com.hanhpk.basekoinkotlin.di.modules

import com.hanhpk.basekoinkotlin.repository.SampleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SampleRepository> { SampleRepository.SampleRepositoryImpl(get()) }
}