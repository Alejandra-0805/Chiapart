package com.alejandra.chiapart.features.home.data.repositories

import com.alejandra.chiapart.features.home.data.datasources.remote.api.HomeApi
import com.alejandra.chiapart.features.home.data.datasources.remote.mapper.toDomainList
import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.domain.repositories.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return api.getProducts().toDomainList()

    }

    override suspend fun searchProducts(query: String): List<Product> {
        val normalizedQuery = query.trim().lowercase()
        return api.getProducts()
            .toDomainList()
            .filter { product ->
                product.name.lowercase().contains(normalizedQuery) ||
                    product.category.lowercase().contains(normalizedQuery) ||
                    product.description.lowercase().contains(normalizedQuery)
            }

    }
}