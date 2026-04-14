package com.alejandra.chiapart.features.addProduct.di

import com.alejandra.chiapart.features.addProduct.data.repositories.AddProductRepositoryImpl
import com.alejandra.chiapart.features.addProduct.domain.repositories.AddProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AddProductRepositoryModule {
    @Binds
    abstract fun bindAddProductRepository(
        addProductRepositoryImpl: AddProductRepositoryImpl
    ): AddProductRepository
}
