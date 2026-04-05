package com.alejandra.chiapart.features.home.presentation.mapper

import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.presentation.models.ProductUiModel

fun List<Product>.toUiModelList(): List<ProductUiModel> = map { product ->
    ProductUiModel(
        id = product.id,
        name = product.name,
        category = product.category,
        description = product.description,
        price = product.price
    )
}