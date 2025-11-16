package dev.bober.store.data.repository

import android.content.Context
import dev.bober.store.data.source.RemoteDataSource
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppsDownloadHistoryModel
import dev.bober.store.domain.AppsListModel
import dev.bober.store.domain.AppsViewHistoryModel
import dev.bober.store.domain.TagModel

class AppsRepository(
    private val source: RemoteDataSource
) {
    suspend fun getAppsList(
        tag: String?,
        filter: String?,
        search: String?,
        sort: String?,
        order: String?
    ): Resource<AppsListModel> {
        return when (val res = source.getAppsList(
            tag = tag,
            filter = filter,
            search = search,
            sort = sort,
            order = order
        )) {
            is Resource.Success -> {
                Resource.Success(data = res.data?.toModel() ?: AppsListModel())
            }
            is Resource.Error -> {
                Resource.Error(res.code, res.error)
            }
            is Resource.Loading -> {
                Resource.Loading()
            }
        }
    }

    suspend fun getViewsHistory(token: String): Resource<AppsViewHistoryModel> {
        return when (val res = source.getViewsHistory(token)) {
            is Resource.Success -> {
                Resource.Success(data = res.data?.toModel() ?: AppsViewHistoryModel())
            }

            is Resource.Error -> {
                Resource.Error(res.code, res.error)
            }
            is Resource.Loading -> {
                Resource.Loading()
            }
        }
    }

    suspend fun getDownloadsHistory(token: String): Resource<AppsDownloadHistoryModel> {
        return when (val res = source.getDownloadsHistory(token)) {
            is Resource.Success -> {
                Resource.Success(data = res.data?.toModel() ?: AppsDownloadHistoryModel())
            }

            is Resource.Error -> {
                Resource.Error(res.code, res.error)
            }
            is Resource.Loading -> {
                Resource.Loading()
            }
        }
    }

    suspend fun getSimilar(appId: Int): Resource<AppsListModel> {
        return when(val res = source.getSimilar(appId)){
            is Resource.Error -> Resource.Error(res.code, res.error)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(data = res.data?.toModel() ?: AppsListModel())
        }
    }

    suspend fun appViewed(
        appId: String,
        token: String
    ) {
        source.appViewed(appId, token)
    }


    suspend fun getTags(): Resource<TagModel> {
        return when (val res = source.getTags()) {
            is Resource.Error -> Resource.Error(res.code, res.error)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(data = res.data?.toModel() ?: TagModel())
        }
    }

    suspend fun downloadApp(
        context: Context,
        appId: String,
        fileName: String,
        token: String,
    ) = source.downloadApp(context, appId, fileName, token)
}