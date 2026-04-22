package com.alejandra.chiapart.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Serializable
data class ProductDetails(val productId: Int)

@Serializable
object AddProduct
