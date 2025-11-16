package dev.bober.store.data.dto.auth

import androidx.annotation.Keep
import dev.bober.store.domain.LoginResponseModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LoginResponseDto(
    val data: TokenDto
) {
    fun toModel() = LoginResponseModel(
        accessToken = data.accessToken,
        refreshToken = data.refreshToken
    )
}
