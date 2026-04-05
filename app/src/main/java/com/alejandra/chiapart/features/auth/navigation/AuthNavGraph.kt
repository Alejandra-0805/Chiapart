package com.alejandra.chiapart.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.core.navigation.Login
import com.alejandra.chiapart.core.navigation.Register
import com.alejandra.chiapart.features.auth.presentation.screens.LoginScreen
import com.alejandra.chiapart.features.auth.presentation.screens.RegisterScreen
import javax.inject.Inject

class AuthNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    // Navegar a Home u otra pantalla principal
                    navController.navigate(com.alejandra.chiapart.core.navigation.Home) {
                        popUpTo<Login> { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Register)
                }
            )
        }

        navGraphBuilder.composable<Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    // Navegar a Home u otra pantalla principal
                    navController.navigate(com.alejandra.chiapart.core.navigation.Home) {
                        popUpTo<Register> { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}