package com.alejandra.chiapart.features.home.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("precio")
    val precio: Double,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("imagenUrl")
    val imagenUrl: String,
    @SerializedName("categoriaId")
    val categoriaId: Int,
    @SerializedName("categoriaNombre")
    val categoriaNombre: String,
    @SerializedName("regionId")
    val regionId: Int,
    @SerializedName("regionNombre")
    val regionNombre: String,
    @SerializedName("usuarioId")
    val usuarioId: Int
)
