package com.alejandra.chiapart.features.addProduct.domain.usecases

import javax.inject.Inject

data class AddProductUseCases @Inject constructor(
    val getCategories: GetCategoriesUseCase,
    val getRegions: GetRegionsUseCase,
    val createProduct: CreateProductUseCase
)
