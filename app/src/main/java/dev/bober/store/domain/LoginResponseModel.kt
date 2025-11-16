package dev.bober.store.domain


data class LoginResponseModel(
    val accessToken: String = "",
    val refreshToken: String = "",
)
