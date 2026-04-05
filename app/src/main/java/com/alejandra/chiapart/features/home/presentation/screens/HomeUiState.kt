package com.alejandra.chiapart.features.home.presentation.screens

import com.alejandra.chiapart.features.home.domain.entities.Product

data class HomeUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val products: List<Product> = emptyList(),
    val error: String? = null
)