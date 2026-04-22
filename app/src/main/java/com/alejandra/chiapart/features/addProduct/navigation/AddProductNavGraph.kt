package com.alejandra.chiapart.features.addProduct.navigation

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.core.navigation.AddProduct
import com.alejandra.chiapart.features.addProduct.presentation.screens.AddProductScreen
import javax.inject.Inject

class AddProductNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<AddProduct> {
            val context = LocalContext.current
            AddProductScreen(
                onNavigateBack = { navController.popBackStack() },
                onProductCreated = {
                    Toast.makeText(context, "Producto creado exitosamente", Toast.LENGTH_SHORT).show()
                    navController.popBackStack() 
                }
            )
        }
    }
}