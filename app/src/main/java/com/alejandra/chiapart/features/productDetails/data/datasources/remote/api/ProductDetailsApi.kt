package com.alejandra.chiapart.features.productDetails.data.datasources.remote.api

import com.alejandra.chiapart.features.productDetails.data.datasources.remote.model.ProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductDetailsApi {
    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") productId: Int): ProductDto

    @PUT("products/{productId}")
    suspend fun updateProduct(@Path("productId") productId: Int, @Body product: ProductDto): ProductDto

    @DELETE("products/{productId}")
    suspend fun deleteProduct(@Path("productId") productId: Int): Boolean


}