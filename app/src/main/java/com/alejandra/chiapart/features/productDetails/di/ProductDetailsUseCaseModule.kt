package com.alejandra.chiapart.features.productDetails.di

import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import com.alejandra.chiapart.features.productDetails.domain.usecases.DeleteProductUseCase
import com.alejandra.chiapart.features.productDetails.domain.usecases.EditProductUseCase
import com.alejandra.chiapart.features.productDetails.domain.usecases.GetProductDetailsUseCase
import com.alejandra.chiapart.features.productDetails.domain.usecases.ProductDetailsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDetailsUseCaseModule {
    @Provides
    @Singleton
    fun provideProductDetailsUseCases(
        repository: ProductRepository
    ): ProductDetailsUseCases {
        return ProductDetailsUseCases(
            getProductDetails = GetProductDetailsUseCase(repository),
            editProduct = EditProductUseCase(repository),
            deleteProduct = DeleteProductUseCase(repository)
        )
    }
}
