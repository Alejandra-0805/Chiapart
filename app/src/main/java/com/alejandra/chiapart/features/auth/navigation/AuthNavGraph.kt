package com.alejandra.chiapart.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.core.navigation.Home
import com.alejandra.chiapart.core.navigation.Login
import com.alejandra.chiapart.core.navigation.Register
import com.alejandra.chiapart.core.navigation.Splash
import com.alejandra.chiapart.features.auth.presentation.screens.LoginScreen
import com.alejandra.chiapart.features.auth.presentation.screens.RegisterScreen
import com.alejandra.chiapart.features.auth.presentation.screens.SplashScreen
import javax.inject.Inject

class AuthNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        // Pantalla inicial: verifica si hay sesión activa
        navGraphBuilder.composable<Splash> {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo<Splash> { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            )
        }

        navGraphBuilder.composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Home) {
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
                    navController.navigate(Home) {
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