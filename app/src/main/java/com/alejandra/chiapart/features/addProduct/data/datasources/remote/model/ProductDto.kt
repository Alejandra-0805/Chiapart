package com.alejandra.chiapart.features.addProduct.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String
)

data class RegionDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String
)

data class CreateProductResponseDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("mensaje")
    val mensaje: String?,
    @SerializedName("exito")
    val exito: Boolean?
)
