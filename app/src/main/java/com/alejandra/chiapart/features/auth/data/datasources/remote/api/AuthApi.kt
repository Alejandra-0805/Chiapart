package com.alejandra.chiapart.features.auth.data.datasources.remote.api

import com.alejandra.chiapart.features.auth.data.datasources.remote.model.LoginRequest
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.LoginResponseDto
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.RegisterRequest
import com.alejandra.chiapart.features.auth.data.datasources.remote.model.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("users/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponseDto

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponseDto

    /**
     * Verifica si el token guardado sigue siendo válido.
     * Retorna 200 si el token es válido, 401 si expiró o es inválido.
     */
    @GET("users/me")
    suspend fun verifyToken(): Response<Unit>
}