package com.alejandra.chiapart.features.home.domain.entities

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val region: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)