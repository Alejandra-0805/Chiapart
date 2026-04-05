package com.alejandra.chiapart.core.di

import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.features.home.navigation.HomeNavGraph
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
    abstract fun bindHomeNavGraph(impl: HomeNavGraph): FeatureNavGraph

}