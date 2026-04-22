package com.alejandra.chiapart.features.addProduct.domain.usecases

import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.repositories.AddProductRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: AddProductRepository
) {
    suspend operator fun invoke(): Result<List<Category>> {
        return try {
            val categories = repository.getCategories()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
