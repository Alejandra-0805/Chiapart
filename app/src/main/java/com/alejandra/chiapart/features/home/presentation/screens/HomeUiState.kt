package com.alejandra.chiapart.features.home.presentation.screens

import com.alejandra.chiapart.features.home.presentation.models.ProductUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val products: List<ProductUiModel> = emptyList(),
    val error: String? = null
)