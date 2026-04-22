package com.alejandra.chiapart.features.addProduct.data.datasources.remote.mapper

import com.alejandra.chiapart.features.addProduct.data.datasources.remote.model.CategoryDto
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.model.CreateProductResponseDto
import com.alejandra.chiapart.features.addProduct.data.datasources.remote.model.RegionDto
import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductResponse
import com.alejandra.chiapart.features.addProduct.domain.entities.Region

fun CategoryDto.toDomain(): Category {
    return Category(
        id = this.id,
        name = this.nombre
    )
}

fun List<CategoryDto>.toDomainCategories(): List<Category> = map { it.toDomain() }

fun RegionDto.toDomain(): Region {
    return Region(
        id = this.id,
        name = this.nombre
    )
}

fun List<RegionDto>.toDomainRegions(): List<Region> = map { it.toDomain() }

fun CreateProductResponseDto.toDomain(): CreateProductResponse {
    return CreateProductResponse(
        success = this.exito ?: false,
        message = this.mensaje ?: "",
        productId = this.id
    )
}