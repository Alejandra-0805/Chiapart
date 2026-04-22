package com.alejandra.chiapart.features.addProduct.di

import com.alejandra.chiapart.features.addProduct.data.datasources.remote.api.AddProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddProductNetworkModule {
    @Provides
    @Singleton
    fun provideAddProductApi(retrofit: Retrofit): AddProductApi {
        return retrofit.create(AddProductApi::class.java)
    }
}
