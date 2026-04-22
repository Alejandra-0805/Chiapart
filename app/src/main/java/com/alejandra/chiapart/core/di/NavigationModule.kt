package com.alejandra.chiapart.core.di

import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.features.auth.navigation.AuthNavGraph
import com.alejandra.chiapart.features.home.navigation.HomeNavGraph
import com.alejandra.chiapart.features.productDetails.navigation.ProductDetailsNavGraph
import com.alejandra.chiapart.features.addProduct.navigation.AddProductNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @IntoSet
    abstract fun bindAuthNavGraph(impl: AuthNavGraph): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindHomeNavGraph(impl: HomeNavGraph): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindProductDetailsNavGraph(impl: ProductDetailsNavGraph): FeatureNavGraph

    @Binds
    @IntoSet
    abstract fun bindAddProductNavGraph(impl: AddProductNavGraph): FeatureNavGraph
}