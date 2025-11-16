package dev.bober.store.domain

import java.time.LocalDateTime

data class AppDownloadHistoryModel(
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
    val similarApps: String?,
    val downloadedAt: LocalDateTime,
) {
    fun toAppModel() = AppModel(
        appId = appId,
        name = name,
        rating = rating,
        category = category,
        smallIconId = smallIconId,
        bigIconId = bigIconId,
        appCardScreenshotsIds = appCardScreenshotsIds,
        downloads = downloads,
        developerName = developerName,
        developerId = developerId,
        releaseDate = releaseDate,
        ageRestriction = ageRestriction,
        description = description,
        editorChoice = editorChoice,
        similarApps = similarApps,
    )
}
