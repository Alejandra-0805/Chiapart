package com.alejandra.chiapart.features.auth.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("passwordRaw")
    val passwordRaw: String,
    @SerializedName("username")
    val username: String
)
