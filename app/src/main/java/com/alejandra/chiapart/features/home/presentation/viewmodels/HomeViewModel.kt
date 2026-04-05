package com.alejandra.chiapart.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandra.chiapart.features.home.domain.usecases.HomeUseCases
import com.alejandra.chiapart.features.home.presentation.screens.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchProducts()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query)
        }
        fetchProducts()
    }

    fun onRetry() {
        fetchProducts()
    }

    private fun fetchProducts() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val query = _uiState.value.searchQuery.trim()

            _uiState.update { currentState ->
                currentState.copy(isLoading = true, error = null)
            }

            if (query.isNotBlank()) {
                delay(250)
            }

            runCatching {
                if (query.isBlank()) {
                    homeUseCases.getProducts()
                } else {
                    homeUseCases.searchProducts(query)
                }
            }.onSuccess { products ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        products = products,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = throwable.message ?: "No se pudo cargar la informacion."
                    )
                }
            }
        }
    }
}