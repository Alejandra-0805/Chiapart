package com.alejandra.chiapart.features.addProduct.data.datasources.remote.api

import com.alejandra.chiapart.features.addProduct.data.datasources.remote.model.CategoryDto
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.model.CreateProductResponseDto
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.model.RegionDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AddProductApi {
    @GET("catalog/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("catalog/regions")
    suspend fun getRegions(): List<RegionDto>

    @Multipart
    @POST("products")
    suspend fun createProduct(
        @Part("nombre") nombre: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("categoriaId") categoriaId: RequestBody,
        @Part("regionId") regionId: RequestBody,
        @Part imagen: MultipartBody.Part?
    ): CreateProductResponseDto
}