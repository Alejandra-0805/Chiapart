package com.alejandra.chiapart.features.productDetails.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        ProductDetailsNetworkModule::class,
        ProductDetailsUseCaseModule::class
    ]
)
@InstallIn(SingletonComponent::class)
interface ProductDetailsModule