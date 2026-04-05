package com.alejandra.chiapart.features.home.di

import com.alejandra.chiapart.features.home.domain.repositories.ProductRepository
import com.alejandra.chiapart.features.home.domain.usecases.GetProductsUseCase
import com.alejandra.chiapart.features.home.domain.usecases.HomeUseCases
import com.alejandra.chiapart.features.home.domain.usecases.SearchProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeUseCaseModule {

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchProductsUseCase(repository: ProductRepository): SearchProductsUseCase {
        return SearchProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        getProductsUseCase: GetProductsUseCase,
        searchProductsUseCase: SearchProductsUseCase,
    ): HomeUseCases {
        return HomeUseCases(
            getProducts = getProductsUseCase,
            searchProducts = searchProductsUseCase
        )
    }
}