package com.alejandra.chiapart.features.productDetails.data.repositories

import com.alejandra.chiapart.features.productDetails.data.datasources.remote.api.ProductDetailsApi
import com.alejandra.chiapart.features.productDetails.data.datasources.remote.mapper.toDomain
import com.alejandra.chiapart.features.productDetails.data.datasources.remote.mapper.toDto
import com.alejandra.chiapart.features.productDetails.domain.entities.Product
import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductDetailsApi
) : ProductRepository {
    override suspend fun getProductDetails(productId: Int): Product {
        return api.getProductById(productId).toDomain()
    }

    override suspend fun updateProduct(product: Product): Product {
        return api.updateProduct(product.id, product.toDto()).toDomain()
    }

    override suspend fun deleteProduct(productId: Int): Boolean {
        return api.deleteProduct(productId)
    }
}