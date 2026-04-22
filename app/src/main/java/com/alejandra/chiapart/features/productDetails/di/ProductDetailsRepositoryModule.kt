package com.alejandra.chiapart.features.productDetails.di

import com.alejandra.chiapart.features.productDetails.data.repositories.ProductRepositoryImpl
import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductDetailsRepositoryModule {
    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
