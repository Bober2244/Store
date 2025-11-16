package dev.bober.store.data.dto.auth

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LoginBodyDto(
    val email: String,
    val password: String
)