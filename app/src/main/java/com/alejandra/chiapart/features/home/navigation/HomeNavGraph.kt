package com.alejandra.chiapart.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.core.navigation.Home
import com.alejandra.chiapart.core.navigation.ProductDetails
import com.alejandra.chiapart.features.home.presentation.screens.HomeScreen
import javax.inject.Inject

class HomeNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Home> {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(ProductDetails(productId))
                }
            )
        }
    }
}