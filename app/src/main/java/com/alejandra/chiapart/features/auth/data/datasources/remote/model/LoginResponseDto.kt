package com.alejandra.chiapart.features.auth.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String
)
