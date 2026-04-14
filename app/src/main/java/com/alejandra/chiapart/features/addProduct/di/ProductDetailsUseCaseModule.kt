package com.alejandra.chiapart.features.addProduct.di

import com.alejandra.chiapart.features.addProduct.domain.repositories.AddProductRepository
import com.alejandra.chiapart.features.addProduct.domain.usecases.AddProductUseCases
import com.alejandra.chiapart.features.addProduct.domain.usecases.CreateProductUseCase
import com.alejandra.chiapart.features.addProduct.domain.usecases.GetCategoriesUseCase
import com.alejandra.chiapart.features.addProduct.domain.usecases.GetRegionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddProductUseCaseModule {
    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        repository: AddProductRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRegionsUseCase(
        repository: AddProductRepository
    ): GetRegionsUseCase {
        return GetRegionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateProductUseCase(
        repository: AddProductRepository
    ): CreateProductUseCase {
        return CreateProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddProductUseCases(
        getCategoriesUseCase: GetCategoriesUseCase,
        getRegionsUseCase: GetRegionsUseCase,
        createProductUseCase: CreateProductUseCase
    ): AddProductUseCases {
        return AddProductUseCases(
            getCategories = getCategoriesUseCase,
            getRegions = getRegionsUseCase,
            createProduct = createProductUseCase
        )
    }
}
