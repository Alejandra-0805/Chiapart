package com.alejandra.chiapart.features.auth.domain.repositories

import com.alejandra.chiapart.features.auth.data.datasources.remote.model.LoginRequest
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.User
import com.alejandra.chiapart.features.auth.domain.entities.LoginResponse
import com.alejandra.chiapart.features.auth.domain.entities.RegisterResponse

interface AuthRepository {
    suspend fun register(user: User): RegisterResponse

    suspend fun login(request: LoginRequest): LoginResponse
}