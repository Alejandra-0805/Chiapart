package com.alejandra.chiapart.features.home.data.datasources.remote.api

import com.alejandra.chiapart.features.home.data.datasources.remote.model.ProductDto
import retrofit2.http.GET

interface HomeApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

}