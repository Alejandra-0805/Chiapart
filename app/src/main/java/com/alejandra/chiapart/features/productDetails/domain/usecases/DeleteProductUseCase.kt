package com.alejandra.chiapart.features.productDetails.domain.usecases

import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Result<Boolean> {
        return try {
            val result = repository.deleteProduct(productId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}