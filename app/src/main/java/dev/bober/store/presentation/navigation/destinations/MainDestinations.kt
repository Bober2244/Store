package dev.bober.store.presentation.navigation.destinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val graphRoute: @Serializable Any
) {
    HOME("Главная", Icons.Default.Home, HomeGraph),
    CATEGORIES("Категории", Icons.Default.Category, CategoriesGraph),
}