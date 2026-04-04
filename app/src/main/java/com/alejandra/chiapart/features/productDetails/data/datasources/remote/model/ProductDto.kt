package com.alejandra.chiapart.features.productDetails.data.datasources.remote.model

data class ProductDto(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val price: Double
)
