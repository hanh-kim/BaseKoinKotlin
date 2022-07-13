package com.hanhpk.basekoinkotlin

import android.app.Application
import com.hanhpk.basekoinkotlin.common.NetworkHandler
import com.hanhpk.basekoinkotlin.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidApplication : Application() {
companion object{
    lateinit var mInstance: AndroidApplication
}

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(listOf(networkModule,apiServiceModule, retrofitModule, viewModelModule, repositoryModule, handlerCaseModule))
        }
        mInstance = this
    }

    private val networkModule = module {
        single { NetworkHandler(applicationContext) }
    }
}