package dev.bober.store.data.dto.apps

import androidx.annotation.Keep
import dev.bober.store.domain.AppsDownloadHistoryModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppsDownloadHistoryDto(
    val data: List<AppDownloadHistoryDto>
) {
    fun toModel() = AppsDownloadHistoryModel(
        data = data.map { it.toModel() }
    )
}