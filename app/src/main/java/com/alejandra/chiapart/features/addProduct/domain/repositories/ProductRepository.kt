package com.alejandra.chiapart.features.addProduct.domain.repositories

import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductRequest
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductResponse
import com.alejandra.chiapart.features.addProduct.domain.entities.Region

interface AddProductRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getRegions(): List<Region>
    suspend fun createProduct(request: CreateProductRequest, imageBytes: ByteArray?): CreateProductResponse
}