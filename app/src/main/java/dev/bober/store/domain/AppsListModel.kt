package dev.bober.store.domain

data class AppsListModel(
    val responseCode: Int? = null,
    val data: List<AppModel> = emptyList()
)