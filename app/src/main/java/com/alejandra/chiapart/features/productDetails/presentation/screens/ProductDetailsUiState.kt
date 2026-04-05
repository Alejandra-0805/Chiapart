package com.alejandra.chiapart.features.productDetails.presentation.screens

import com.alejandra.chiapart.features.productDetails.domain.entities.Product

data class ProductDetailsUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val isSuccess: Boolean = false,
    val isDeleted: Boolean = false,
    val message: String? = null,
    val error: String? = null
)
