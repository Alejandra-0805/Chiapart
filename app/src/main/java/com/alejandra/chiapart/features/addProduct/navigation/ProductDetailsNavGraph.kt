package com.alejandra.chiapart.features.addProduct.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.core.navigation.ProductDetails
import com.alejandra.chiapart.features.productDetails.presentation.screens.ProductDetailsScreen
import javax.inject.Inject

class ProductDetailsNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<ProductDetails> { backStackEntry ->
            val productDetails: ProductDetails = backStackEntry.toRoute()
            ProductDetailsScreen(
                productId = productDetails.productId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}