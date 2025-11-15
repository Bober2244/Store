package dev.bober.store.domain

import kotlinx.serialization.Serializable

@Serializable
data class AppModel(
    val appId: Int,
    val name: String,
    val rating: Double,
    val category: String,
    val smallIconId: Int?,
    val bigIconId: Int?,
    val appCardScreenshotsIds: List<Int>?,
    val downloads: Long,
    val developerName: String,
    val developerId: Int,
    val releaseDate: String,
    val ageRestriction: String,
    val description: String,
    val editorChoice: Boolean,
    val similarApps: String?
)
