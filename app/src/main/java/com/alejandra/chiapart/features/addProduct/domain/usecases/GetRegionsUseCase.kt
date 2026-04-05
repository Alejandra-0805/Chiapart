package com.alejandra.chiapart.features.addProduct.domain.usecases

import com.alejandra.chiapart.features.addProduct.domain.entities.Region
import com.alejandra.chiapart.features.addProduct.domain.repositories.AddProductRepository
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(
    private val repository: AddProductRepository
) {
    suspend operator fun invoke(): Result<List<Region>> {
        return try {
            val regions = repository.getRegions()
            Result.success(regions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
