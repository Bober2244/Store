package dev.bober.store.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = ColorLightBlue,
    primaryContainer = ColorLightBlue,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF252525),
    tertiary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = ColorLightBlue,
    primaryContainer = ColorLightBlue,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFABABAB),
    tertiary = Color.Black,
)

@Composable
fun StoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}