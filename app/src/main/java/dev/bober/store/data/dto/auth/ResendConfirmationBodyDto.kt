package dev.bober.store.data.dto.auth

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ResendConfirmationBodyDto(
    val email: String
)