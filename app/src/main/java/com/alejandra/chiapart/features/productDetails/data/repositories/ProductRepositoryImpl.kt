package com.alejandra.chiapart.features.productDetails.data.repositories

import android.util.Log
import com.alejandra.chiapart.features.productDetails.data.datasources.remote.api.ProductDetailsApi
import com.alejandra.chiapart.features.productDetails.data.datasources.remote.mapper.toDomain
import com.alejandra.chiapart.features.productDetails.data.datasources.remote.mapper.toDto
import com.alejandra.chiapart.features.productDetails.domain.entities.Product
import com.alejandra.chiapart.features.productDetails.domain.repositories.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductDetailsApi
) : ProductRepository {
    override suspend fun getProductDetails(productId: Int): Product {
        return api.getProductById(productId).toDomain()
    }

    override suspend fun updateProduct(product: Product): Product {
        val nombreBody = product.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val precioBody = product.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val descripcionBody = product.description.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoriaBody = product.categoryId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val regionBody = product.regionId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        // Check if imageUrl is a local file path
        var imagenPart: MultipartBody.Part? = null
        if (product.imageUrl.isNotEmpty() && !product.imageUrl.startsWith("http")) {
            val file = File(product.imageUrl)
            if (file.exists()) {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                imagenPart = MultipartBody.Part.createFormData("imagen", file.name, requestFile)
            }
        }

        val response = api.updateProduct(
            productId = product.id,
            nombre = nombreBody,
            precio = precioBody,
            descripcion = descripcionBody,
            categoriaId = categoriaBody,
            regionId = regionBody,
            imagen = imagenPart
        )

        if (response.isSuccessful) {
            Log.d("ProductRepositoryImpl", "Updated product: $product")
            return product
        } else {
            throw Exception("Failed to update product: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun deleteProduct(productId: Int): Boolean {
        val response = api.deleteProduct(productId)
        return response.isSuccessful
    }
}