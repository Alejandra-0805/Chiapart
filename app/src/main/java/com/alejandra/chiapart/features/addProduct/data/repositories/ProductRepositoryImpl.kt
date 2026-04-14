package com.alejandra.chiapart.features.addProduct.data.repositories

import com.alejandra.chiapart.features.addProduct.data.datasources.remote.api.AddProductApi
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.mapper.toDomain
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.mapper.toDomainCategories
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.mapper.toDomainRegions
import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductRequest
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductResponse
import com.alejandra.chiapart.features.addProduct.domain.entities.Region
import com.alejandra.chiapart.features.addProduct.domain.repositories.AddProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(
    private val api: AddProductApi
) : AddProductRepository {

    override suspend fun getCategories(): List<Category> {
        return api.getCategories().toDomainCategories()
    }

    override suspend fun getRegions(): List<Region> {
        return api.getRegions().toDomainRegions()
    }

    override suspend fun createProduct(
        request: CreateProductRequest,
        imageBytes: ByteArray?
    ): CreateProductResponse {
        val nombreBody = request.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val precioBody = request.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val descripcionBody = request.description.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoriaIdBody = request.categoryId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val regionIdBody = request.regionId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val imagenPart = imageBytes?.let { bytes ->
            val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("imagen", "product_image.jpg", requestBody)
        }

        return api.createProduct(
            nombre = nombreBody,
            precio = precioBody,
            descripcion = descripcionBody,
            categoriaId = categoriaIdBody,
            regionId = regionIdBody,
            imagen = imagenPart
        ).toDomain()
    }
}