package dev.bober.store.data.dto.apps

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import dev.bober.store.domain.AppDownloadHistoryModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Keep
@Serializable
data class AppDownloadHistoryDto(
    @SerialName("AppID")
    @SerializedName("AppID")
    val appId: Int,
    @SerialName("AppName")
    @SerializedName("AppName")
    val appName: String,
    @SerialName("SmallIconID")
    @SerializedName("SmallIconID")
    val smallIconId: Int?,
    @SerialName("BigIconID")
    @SerializedName("BigIconID")
    val bigIconId: Int?,
    @SerialName("AppCardScreenshotsIDs")
    @SerializedName("AppCardScreenshotsIDs")
    val appCardScreenshotsIds: String?,
    @SerialName("Rating")
    @SerializedName("Rating")
    val rating: Double,
    @SerialName("Downloads")
    @SerializedName("Downloads")
    val downloads: Long,
    @SerialName("Categories")
    @SerializedName("Categories")
    val categories: String,
    @SerialName("DeveloperName")
    @SerializedName("DeveloperName")
    val developerName: String,
    @SerialName("DeveloperID")
    @SerializedName("DeveloperID")
    val developerId: Int,
    @SerialName("ReleaseDate")
    @SerializedName("ReleaseDate")
    val releaseDate: String,
    @SerialName("AgeRestriction")
    @SerializedName("AgeRestriction")
    val ageRestriction: String,
    @SerialName("Description")
    @SerializedName("Description")
    val description: String,
    @SerialName("EditorChoice")
    @SerializedName("EditorChoice")
    val editorChoice: Int,
    @SerialName("SimilarApps")
    @SerializedName("SimilarApps")
    val similarApps: String?,
    @SerialName("downloaded_at")
    @SerializedName("downloaded_at")
    val downloadedAt: String
) {
    fun toModel(): AppDownloadHistoryModel {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val dateTime: LocalDateTime = LocalDateTime.parse(downloadedAt, formatter)
        return AppDownloadHistoryModel(
            appId = appId,
            name = appName,
            rating = rating,
            category = categories,
            smallIconId = smallIconId,
            bigIconId = bigIconId,
            appCardScreenshotsIds = appCardScreenshotsIds?.split(",")?.map { it.toInt() },
            downloads = downloads,
            developerName = developerName,
            developerId = developerId,
            releaseDate = releaseDate,
            ageRestriction = ageRestriction,
            description = description,
            editorChoice = editorChoice == 1,
            similarApps = similarApps,
            downloadedAt = dateTime
        )
    }
}