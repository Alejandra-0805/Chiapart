package com.alejandra.chiapart.features.addProduct.domain.usecases

import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductRequest
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductResponse
import com.alejandra.chiapart.features.addProduct.domain.repositories.AddProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: AddProductRepository
) {
    suspend operator fun invoke(
        request: CreateProductRequest,
        imageBytes: ByteArray?
    ): Result<CreateProductResponse> {
        return try {
            val response = repository.createProduct(request, imageBytes)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
