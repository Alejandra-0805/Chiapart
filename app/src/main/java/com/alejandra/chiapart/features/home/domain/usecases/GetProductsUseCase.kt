package com.alejandra.chiapart.features.home.domain.usecases

import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }

}