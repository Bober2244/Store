package dev.bober.store.data.dto.apps

import androidx.annotation.Keep
import dev.bober.store.domain.AppsViewHistoryModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppsViewHistoryDto(
    val data: List<AppViewHistoryDto>
) {
    fun toModel() = AppsViewHistoryModel(
        data.map { it.toModel() }
    )
}
