package com.alejandra.chiapart.features.productDetails.domain.entities

data class Product (
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val price: Double
)