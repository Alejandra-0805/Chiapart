package com.alejandra.chiapart.features.productDetails.domain.repositories

import com.alejandra.chiapart.features.productDetails.domain.entities.Product

interface ProductRepository {
    suspend fun getProductDetails(productId: Int): Product
    suspend fun updateProduct(product: Product): Product
    suspend fun deleteProduct(productId: Int): Boolean
}