package dev.bober.store.presentation.navigation.destinations

import dev.bober.store.domain.AppModel
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

@Serializable
object HomeRoute

@Serializable
data class AppDetailsRoute(
    val app: AppModel
)