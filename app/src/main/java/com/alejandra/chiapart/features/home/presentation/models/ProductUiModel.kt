package com.alejandra.chiapart.features.home.presentation.models

data class ProductUiModel(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val price: Double
)