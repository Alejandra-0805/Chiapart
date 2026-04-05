package com.alejandra.chiapart.features.home.domain.usecases

import javax.inject.Inject

data class HomeUseCases @Inject constructor(
    val getProducts: GetProductsUseCase,
    val searchProducts: SearchProductsUseCase,
)
