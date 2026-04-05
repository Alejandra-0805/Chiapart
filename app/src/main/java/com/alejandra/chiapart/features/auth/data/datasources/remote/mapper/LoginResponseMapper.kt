package com.alejandra.chiapart.features.auth.data.datasources.remote.mapper

import com.alejandra.chiapart.features.auth.data.datasources.remote.model.LoginResponseDto
import com.alejandra.chiapart.features.auth.domain.entities.LoginResponse

fun LoginResponseDto.loginToDomain(): LoginResponse {
    return LoginResponse(
        message = this.message,
        token = this.token
    )
}