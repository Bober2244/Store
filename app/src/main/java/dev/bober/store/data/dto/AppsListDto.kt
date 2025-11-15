package dev.bober.store.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import dev.bober.store.domain.AppsListModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppsListDto(
    val data: List<AppDto>
) {
    fun toModel() = AppsListModel(
        data = data.map { it.toModel() }
    )
}
