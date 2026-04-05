package com.alejandra.chiapart.features.home.di

import com.alejandra.chiapart.features.home.data.repositories.ProductRepositoryImpl
import com.alejandra.chiapart.features.home.domain.repositories.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {
    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
