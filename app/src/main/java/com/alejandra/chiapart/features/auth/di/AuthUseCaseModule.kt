package com.alejandra.chiapart.features.auth.di

import com.alejandra.chiapart.core.storage.TokenDataStore
import com.alejandra.chiapart.features.auth.domain.repositories.AuthRepository
import com.alejandra.chiapart.features.auth.domain.usecases.AuthUseCases
import com.alejandra.chiapart.features.auth.domain.usecases.LoginUseCase
import com.alejandra.chiapart.features.auth.domain.usecases.RegisterUseCase
import com.alejandra.chiapart.features.auth.domain.usecases.SaveTokenUseCase
import com.alejandra.chiapart.features.auth.domain.usecases.VerifyTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveTokenUseCase(tokenDataStore: TokenDataStore): SaveTokenUseCase {
        return SaveTokenUseCase(tokenDataStore)
    }

    @Provides
    @Singleton
    fun provideVerifyTokenUseCase(
        tokenDataStore: TokenDataStore,
        authRepository: AuthRepository
    ): VerifyTokenUseCase {
        return VerifyTokenUseCase(tokenDataStore, authRepository)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        loginUseCase: LoginUseCase,
        registerUseCase: RegisterUseCase,
        saveTokenUseCase: SaveTokenUseCase
    ): AuthUseCases {
        return AuthUseCases(
            login = loginUseCase,
            register = registerUseCase,
            saveToken = saveTokenUseCase
        )
    }
}