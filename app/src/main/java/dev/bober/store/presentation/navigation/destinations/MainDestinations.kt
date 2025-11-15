package dev.bober.store.presentation.navigation.destinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val graphRoute: @Serializable Any
) {
    HOME("Главная", Icons.Outlined.Home, HomeGraph),
    CATEGORIES("Категории", Icons.Outlined.Category, CategoriesGraph),
}