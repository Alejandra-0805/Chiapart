package com.alejandra.chiapart.features.home.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double
)
