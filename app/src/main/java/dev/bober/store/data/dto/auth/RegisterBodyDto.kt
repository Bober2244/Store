package dev.bober.store.data.dto.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RegisterBodyDto(
    val email: String,
    val password: String,
    @SerialName("password2")
    @SerializedName("password2")
    val confirmPassword: String,
    @SerialName("first_name")
    @SerializedName("first_name")
    val firstName: String,
    @SerialName("last_name")
    @SerializedName("last_name")
    val lastName: String
)