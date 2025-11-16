package dev.bober.store.data.api

import dev.bober.store.data.dto.apps.AppsListDto
import dev.bober.store.data.dto.apps.TagDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("/api/apps/{app_id}/downloaded")
    suspend fun apkDownloaded(
        @Header("Authorization") token: String,
        @Path("app_id") appId: String
    ): Response<Unit>

    @POST("/api/apps/{app_id}/view")
    suspend fun appViewed(
        @Header("Authorization") token: String,
        @Path("app_id") appId: String
    ): Response<Unit>
}