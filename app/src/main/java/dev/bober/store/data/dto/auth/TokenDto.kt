package dev.bober.store.data.dto.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TokenDto(
    @SerialName("access_token")
    @SerializedName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    @SerializedName("refresh_token")
    val refreshToken: String,
)
