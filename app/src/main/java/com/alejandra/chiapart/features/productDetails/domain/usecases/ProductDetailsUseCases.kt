package com.alejandra.chiapart.features.productDetails.domain.usecases

import javax.inject.Inject

data class ProductDetailsUseCases @Inject constructor(
    val getProductDetails: GetProductDetailsUseCase,
    val editProduct: EditProductUseCase,
    val deleteProduct: DeleteProductUseCase
)