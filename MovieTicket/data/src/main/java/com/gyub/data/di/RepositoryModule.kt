package com.gyub.data.di

import com.gyub.data.repository.MovieRepositoryImpl
import com.gyub.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}