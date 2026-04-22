package com.alejandra.chiapart.features.productDetails.domain.usecases

import com.alejandra.chiapart.features.productDetails.domain.entities.Product
import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Result<Product> {
        return try {
            val product = repository.getProductDetails(productId)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}