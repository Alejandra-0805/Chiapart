package com.alejandra.chiapart.features.auth.data.repositories

import com.alejandra.chiapart.features.auth.data.datasources.remote.api.AuthApi
import com.alejandra.chiapart.features.auth.data.datasources.remote.mapper.loginToDomain
import com.alejandra.chiapart.features.auth.data.datasources.remote.mapper.registerToDomain
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.LoginRequest
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.RegisterRequest
import com.alejandra.chiapart.features.auth.domain.entities.LoginResponse
import com.alejandra.chiapart.features.auth.domain.entities.RegisterResponse
import com.alejandra.chiapart.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun register(request: RegisterRequest): RegisterResponse {
        val response = api.register(request)
        return response.registerToDomain()
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        val response = api.login(request)
        return response.loginToDomain()
    }
}