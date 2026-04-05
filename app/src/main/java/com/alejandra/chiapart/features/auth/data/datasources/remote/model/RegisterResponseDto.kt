package com.alejandra.chiapart.features.auth.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
    @SerializedName("message")
    val message: String
)
