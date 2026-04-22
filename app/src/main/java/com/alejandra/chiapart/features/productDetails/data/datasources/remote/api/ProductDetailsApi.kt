package com.alejandra.chiapart.features.productDetails.data.datasources.remote.api

import com.alejandra.chiapart.features.productDetails.data.datasources.remote.model.ProductDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProductDetailsApi {
    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") productId: Int): ProductDto

    @Multipart
    @PUT("products/{productId}")
    suspend fun updateProduct(
        @Path("productId") productId: Int,
        @Part("nombre") nombre: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("categoriaId") categoriaId: RequestBody,
        @Part("regionId") regionId: RequestBody,
        @Part imagen: MultipartBody.Part?
    ): Response<Unit>

    @DELETE("products/{productId}")
    suspend fun deleteProduct(@Path("productId") productId: Int): Response<Unit>


}