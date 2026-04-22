package com.alejandra.chiapart.features.auth.domain.usecases

import com.alejandra.chiapart.core.storage.TokenDataStore
import com.alejandra.chiapart.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

/**
 * Caso de uso que determina si el usuario tiene una sesión activa válida.
 *
 * Lógica:
 * 1. Si no hay token guardado → [TokenStatus.NoToken]
 * 2. Si hay token y la API devuelve 200 → [TokenStatus.Valid]
 * 3. Si hay token pero la API devuelve 401 → [TokenStatus.Expired] (token inválido/expirado)
 * 4. Si hay error de red → [TokenStatus.NetworkError]
 */
class VerifyTokenUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): TokenStatus {
        val token = tokenDataStore.getToken()

        if (token.isNullOrBlank()) {
            return TokenStatus.NoToken
        }

        return try {
            val isValid = authRepository.verifyToken()
            if (isValid) TokenStatus.Valid else TokenStatus.Expired
        } catch (e: Exception) {
            TokenStatus.NetworkError(e.message ?: "Error de red")
        }
    }
}

sealed class TokenStatus {
    /** No hay ningún token guardado → ir a Login. */
    object NoToken : TokenStatus()

    /** El token existe y la API lo acepta → ir a Home. */
    object Valid : TokenStatus()

    /** El token existe pero la API devuelve 401 → limpiar token e ir a Login. */
    object Expired : TokenStatus()

    /** No se pudo alcanzar la API (sin internet, timeout, etc.). */
    data class NetworkError(val message: String) : TokenStatus()
}
