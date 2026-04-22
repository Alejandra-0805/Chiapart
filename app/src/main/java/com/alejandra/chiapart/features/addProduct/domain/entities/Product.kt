package com.alejandra.chiapart.features.addProduct.domain.entities

data class Category(
    val id: Int,
    val name: String
)

data class Region(
    val id: Int,
    val name: String
)

data class CreateProductRequest(
    val name: String,
    val price: Double,
    val description: String,
    val categoryId: Int,
    val regionId: Int
)

data class CreateProductResponse(
    val success: Boolean,
    val message: String,
    val productId: Int? = null
)