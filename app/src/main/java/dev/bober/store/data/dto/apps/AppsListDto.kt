package dev.bober.store.data.dto.apps

import androidx.annotation.Keep
import dev.bober.store.domain.AppsListModel
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
