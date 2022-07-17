package com.hanhpk.basekoinkotlin.di.modules

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.databinding.ktx.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hanhpk.basekoinkotlin.AndroidApplication
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.base.BaseItem
import com.squareup.moshi.FromJson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Protocol
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 60L
private const val WRITE_TIMEOUT = 60L
private const val READ_TIMEOUT = 60L
private const val BASE_URL = "https://www.flickr.com/services/rest/"

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.networkInterceptors().add(httpLoggingInterceptor);
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

//    single { provideGson() }
//    single { provideHttpClient() }
//    single { provideRetrofit(get(), get()) }

    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }
    single { retrofitHttpClient() }
    single { retrofitBuilder() }
}


private fun Scope.retrofitBuilder(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(get())
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(SNSAccounts.SNSTypeAdapter())
            .build()))
        .build()
}


private fun Scope.retrofitHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            addInterceptor(loggingInterceptor)
        }

        //Todo API Header Token
        val headerInterceptor = Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
               // header("your-token-name", "abcdddDDDD")
               // header("os", "1")
            }.build())
        }
        addInterceptor(headerInterceptor)

        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        protocols(Collections.singletonList(Protocol.HTTP_1_1))
        retryOnConnectionFailure(true)
    }.build()
}

@Parcelize
data class SNSAccounts(
    val sns_type: SNSType,
    val url: String
) : BaseItem(), Parcelable {
    override val layoutResourceId: Int
        get() = R.layout.item_icon_sns

    enum class SNSType(val type: Int, @DrawableRes val icon: Int, val url: String) {
        LINE(1, R.drawable.ic_launcher_background, "https://line.me/"),
        TWITTER(2, R.drawable.ic_launcher_background, "https://twitter.com/"),
        INSTAGRAM(3, R.drawable.ic_launcher_background, "https://www.instagram.com/"),
        FACEBOOK(4, R.drawable.ic_launcher_background, "https://www.facebook.com/"),
        TIKTOK(5, R.drawable.ic_launcher_background, "https://www.tiktok.com/"),
        GMAIL(6, R.drawable.ic_launcher_background, "https://www.google.co.jp/mail/help/intl/ja/about.html?vm=r");

        companion object {
            fun fromIntToSNSType(type: Int) = values().first { it.type == type }
        }
    }

    class SNSTypeAdapter {
        @ToJson
        fun toJson(sns_type: SNSType): Int {
            return sns_type.type
        }

        @FromJson
        fun fromJson(sns_type: Int): SNSType {
            return SNSType.fromIntToSNSType(sns_type)
        }
    }
}
