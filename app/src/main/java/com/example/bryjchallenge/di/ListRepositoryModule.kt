package com.example.bryjchallenge.di

import com.example.bryjchallenge.data.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  ListRepositoryModule {
    @Provides
    @Singleton
    fun provideListRepository(): ListRepository {
        return ListRepository()
    }
}