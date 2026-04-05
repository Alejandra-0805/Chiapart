package com.alejandra.chiapart.features.productDetails.domain.usecases

import com.alejandra.chiapart.features.productDetails.domain.entities.Product
import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import javax.inject.Inject

class EditProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product): Result<Product> {
        return try {
            val updatedProduct = repository.updateProduct(product)
            Result.success(updatedProduct)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}