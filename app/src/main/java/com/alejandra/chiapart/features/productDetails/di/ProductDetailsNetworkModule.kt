package com.alejandra.chiapart.features.productDetails.di

import com.alejandra.chiapart.features.productDetails.data.datasources.remote.api.ProductDetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDetailsNetworkModule {
    @Provides
    @Singleton
    fun provideProductDetailsApi(retrofit: Retrofit): ProductDetailsApi {
        return retrofit.create(ProductDetailsApi::class.java)
    }
}
