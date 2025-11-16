package dev.bober.store.data.api

import dev.bober.store.data.dto.apps.AppsListDto
import dev.bober.store.data.dto.apps.TagDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface AppsApi {
    @GET("/api/apps")
    suspend fun getApps(
        @Query("tag") tag: String?,
        @Query("filter") filter: String?,
        @Query("search") search: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?
    ): Response<AppsListDto>

    @GET("/api/tags")
    suspend fun getTags(): Response<TagDto>

    @Streaming
    @GET("/api/apps/{app_id}/download")
    suspend fun downloadApk(
        @Path("app_id") appId: String,
    ): Response<ResponseBody>
}