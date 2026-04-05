package com.alejandra.chiapart.features.auth.domain.entities

data class LoginResponse(
    val message: String,
    val token: String
)
