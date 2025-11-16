package dev.bober.store.presentation.navigation.destinations

import dev.bober.store.domain.AppModel
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

@Serializable
data class HomeRoute(
    val selectedTag: String? = null
)

@Serializable
data class AppDetailsRoute(
    val app: AppModel
)

@Serializable
object ProfileRoute