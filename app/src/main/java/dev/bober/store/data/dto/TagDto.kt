package dev.bober.store.data.dto

import androidx.annotation.Keep
import dev.bober.store.domain.TagModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TagDto(
    val data: List<String>
) {
    fun toModel() = TagModel(
        data = data
    )
}
