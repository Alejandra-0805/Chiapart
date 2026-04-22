package com.alejandra.chiapart.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandra.chiapart.core.storage.TokenDataStore
import com.alejandra.chiapart.features.auth.domain.usecases.TokenStatus
import com.alejandra.chiapart.features.auth.domain.usecases.VerifyTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Eventos de navegación únicos emitidos desde la pantalla Splash. */
sealed class SplashNavEvent {
    object GoToHome : SplashNavEvent()
    object GoToLogin : SplashNavEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _navEvent = Channel<SplashNavEvent>(Channel.BUFFERED)
    val navEvent = _navEvent.receiveAsFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val status = verifyTokenUseCase()

            when (status) {
                is TokenStatus.Valid -> {
                    _navEvent.send(SplashNavEvent.GoToHome)
                }
                is TokenStatus.Expired -> {
                    // Token inválido o 401 → limpiar y redirigir al login
                    tokenDataStore.clearToken()
                    _navEvent.send(SplashNavEvent.GoToLogin)
                }
                is TokenStatus.NoToken -> {
                    _navEvent.send(SplashNavEvent.GoToLogin)
                }
                is TokenStatus.NetworkError -> {
                    // Sin red: si hay token guardado asumimos sesión activa (offline-first)
                    val token = tokenDataStore.getToken()
                    if (!token.isNullOrBlank()) {
                        _navEvent.send(SplashNavEvent.GoToHome)
                    } else {
                        _navEvent.send(SplashNavEvent.GoToLogin)
                    }
                }
            }
        }
    }
}
