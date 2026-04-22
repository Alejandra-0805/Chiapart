package com.alejandra.chiapart.features.auth.domain.repositories

import com.alejandra.chiapart.features.auth.data.datasources.remote.model.LoginRequest
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.RegisterRequest
import com.alejandra.chiapart.features.auth.domain.entities.LoginResponse
import com.alejandra.chiapart.features.auth.domain.entities.RegisterResponse

interface AuthRepository {
    suspend fun register(request: RegisterRequest): RegisterResponse

    suspend fun login(request: LoginRequest): LoginResponse

    /**
     * Verifica si el token actualmente guardado es válido.
     * @return true si la API responde 2xx, false si responde 401.
     * @throws Exception si hay un error de red u otro error inesperado.
     */
    suspend fun verifyToken(): Boolean
}