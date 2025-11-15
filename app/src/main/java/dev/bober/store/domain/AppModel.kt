package dev.bober.store.domain

import androidx.compose.ui.graphics.vector.ImageVector

data class AppModel(
    val name: String,
    val rating: Double,
    val category: String,
    val icon: ImageVector
)
