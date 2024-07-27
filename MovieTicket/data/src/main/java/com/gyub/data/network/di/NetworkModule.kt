package com.gyub.data.network.di

import android.util.Log
import com.gyub.data.network.util.NetworkUtil.getPrettyLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun providesJson() = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun providesQueryParamInterceptor() = Interceptor { chain: Interceptor.Chain ->
        val originalRequest = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url

        val urlWithApiKey = originalHttpUrl.newBuilder()
            .addQueryParameter("key", "1f709c7970170413506011d595a909c5")
            .build()

        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        chain.proceed(requestWithApiKey)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor { message ->
        Log.d("### Retrofit --", getPrettyLog(message))
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://www.kobis.or.kr/kobisopenapi/webservice/rest/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}