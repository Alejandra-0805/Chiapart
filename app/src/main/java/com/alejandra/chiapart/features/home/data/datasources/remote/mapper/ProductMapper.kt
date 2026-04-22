package com.alejandra.chiapart.features.home.data.datasources.remote.mapper

import com.alejandra.chiapart.features.home.data.datasources.remote.model.ProductDto
import com.alejandra.chiapart.features.home.domain.entities.Product

fun List<ProductDto>.toDomainList(): List<Product> = map { it.toDomain() }

private fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.nombre,
        category = this.categoriaNombre,
        region = this.regionNombre,
        description = this.descripcion,
        price = this.precio,
        imageUrl = this.imagenUrl
    )
}
