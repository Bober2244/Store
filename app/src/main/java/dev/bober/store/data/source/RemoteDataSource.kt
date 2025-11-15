package dev.bober.store.data.source

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import dev.bober.store.data.api.AppsApi
import dev.bober.store.data.dto.AppsListDto
import dev.bober.store.data.utils.Resource
import dev.bober.store.data.utils.safeApiResponse
import dev.bober.store.utils.Constants

class RemoteDataSource(
    private val api: AppsApi,
) {
    suspend fun getAppsList(
        tag: String?,
        filter: String?,
        search: String?,
        sort: String?,
        order: String?
    ): Resource<AppsListDto?> = safeApiResponse {
        api.getApps(
            tag = tag,
            filter = filter,
            search = search,
            sort = sort,
            order = order
        )
    }

    suspend fun getTags() = safeApiResponse {
        api.getTags()
    }

    fun downloadApp(context: Context, appId: String, fileName: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request("${Constants.BASE_URL}/api/apps/${appId}/download".toUri()).apply {
            setMimeType(MIME_TYPE)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setTitle("Скачивание $fileName")
            setDescription("")
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName.apk")
        }
        downloadManager.enqueue(request)
    }

    companion object {
        private const val MIME_TYPE = "application/vnd.android.package-archive"
    }
}