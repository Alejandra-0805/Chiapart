package com.alejandra.chiapart.features.productDetails.data.datasources.remote.mapper

import com.alejandra.chiapart.features.productDetails.data.datasources.remote.model.ProductDto
import com.alejandra.chiapart.features.productDetails.domain.entities.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        category = this.category,
        region = this.region,
        description = this.description,
        price = this.price,
        imageUrl = this.imageUrl,
        usuarioId = this.usuarioId
    )
}

fun Product.toDto(): ProductDto {
    return ProductDto(
        id = this.id,
        name = this.name,
        category = this.category,
        region = this.region,
        description = this.description,
        price = this.price,
        imageUrl = this.imageUrl,
        usuarioId = this.usuarioId
    )
}