package com.alejandra.chiapart.features.home.domain.usecases

import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.domain.repositories.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String): List<Product> {
        return repository.searchProducts(query)
    }
}