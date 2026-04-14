package com.alejandra.chiapart.features.productDetails.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("categoriaNombre")
    val category: String,
    @SerializedName("regionNombre")
    val region: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("precio")
    val price: Double,
    @SerializedName("imagenUrl")
    val imageUrl: String,
    @SerializedName("usuarioId")
    val usuarioId: Int = -1
)
