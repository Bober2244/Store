package dev.bober.store.data.source

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import dev.bober.store.data.api.AppsApi
import dev.bober.store.data.api.AuthApi
import dev.bober.store.data.dto.apps.AppsListDto
import dev.bober.store.data.dto.auth.LoginBodyDto
import dev.bober.store.data.dto.auth.RegisterBodyDto
import dev.bober.store.data.dto.auth.ResendConfirmationBodyDto
import dev.bober.store.data.utils.Resource
import dev.bober.store.data.utils.safeApiResponse
import dev.bober.store.utils.Constants

class RemoteDataSource(
    private val api: AppsApi,
    private val authApi: AuthApi,
) {
    suspend fun getAppsList(
        tag: String?,
        filter: String?,
        search: String?,
        sort: String?,
        order: String?
    ) = safeApiResponse {
        api.getApps(
            tag = tag,
            filter = filter,
            search = search,
            sort = sort,
            order = order
        )
    }

    suspend fun getViewsHistory(token: String) = safeApiResponse {
        api.viewsHistory(token)
    }

    suspend fun getDownloadsHistory(token: String) = safeApiResponse {
        api.downloadsHistory(token)
    }

    suspend fun getSimilar(appId: Int) = safeApiResponse {
        api.getSimilar(appId)
    }

    suspend fun getTags() = safeApiResponse {
        api.getTags()
    }

    suspend fun appViewed(
        appId: String,
        token: String,
    ) = safeApiResponse {
        api.appViewed(
            token = token,
            appId = appId
        )
    }

    suspend fun downloadApp(
        context: Context,
        appId: String,
        fileName: String,
        token: String,
    ): Long {
        safeApiResponse{ api.apkDownloaded(token = token, appId = appId) }
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request =
            DownloadManager.Request("${Constants.BASE_URL}/api/apps/${appId}/download".toUri())
                .apply {
                    setMimeType(MIME_TYPE)
                    setAllowedOverMetered(true)
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setTitle("Скачивание $fileName")
                    setDescription("Пожалуйста, подождите")
                    setDestinationInExternalFilesDir(
                        context,
                        Environment.DIRECTORY_DOWNLOADS,
                        "$fileName.apk"
                    )
                }
        return downloadManager.enqueue(request)
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) = safeApiResponse {
        authApi.register(
            body = RegisterBodyDto(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = passwordConfirmation
            )
        )
    }

    suspend fun login(
        email: String,
        password: String
    ) = safeApiResponse {
        authApi.login(
            body = LoginBodyDto(
                email = email,
                password = password
            )
        )
    }

    suspend fun confirmRegistration(
        token: String
    ) = safeApiResponse {
        authApi.confirmEmail(
            token =  token
        )
    }

    suspend fun resendConfirmation(
        email: String
    ) = safeApiResponse {
        authApi.resendConfirmation(
            body = ResendConfirmationBodyDto(
                email = email
            )
        )
    }

    companion object {
        private const val MIME_TYPE = "application/vnd.android.package-archive"
    }
}