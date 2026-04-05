package com.alejandra.chiapart.features.home.domain.repositories

import com.alejandra.chiapart.features.home.domain.entities.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun searchProducts(query: String): List<Product>

}