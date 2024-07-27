package com.gyub.data.network.di

import com.gyub.data.network.retrofit.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitServiceModule {

    @Singleton
    @Provides
    fun providesMovieService(
        retrofitBuilder: Retrofit,
    ): MovieService = retrofitBuilder.create(MovieService::class.java)

}