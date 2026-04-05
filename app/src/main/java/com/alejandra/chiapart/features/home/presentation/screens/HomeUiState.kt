package com.alejandra.chiapart.features.home.presentation.screens

import com.alejandra.chiapart.features.home.domain.entities.Product

data class HomeUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val allProducts: List<Product> = emptyList(),
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val availableCategories: List<String> = emptyList(),
    val availableRegions: List<String> = emptyList(),
    val selectedCategories: Set<String> = emptySet(),
    val selectedRegions: Set<String> = emptySet()
)