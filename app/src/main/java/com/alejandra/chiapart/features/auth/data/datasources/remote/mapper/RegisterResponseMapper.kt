package com.alejandra.chiapart.features.auth.data.datasources.remote.mapper

import com.alejandra.chiapart.features.auth.data.datasources.remote.model.RegisterResponseDto
import com.alejandra.chiapart.features.auth.domain.entities.RegisterResponse

fun RegisterResponseDto.registerToDomain(): RegisterResponse {
    return RegisterResponse(
        id = this.id,
        email = this.email,
        message = this.message
    )
}