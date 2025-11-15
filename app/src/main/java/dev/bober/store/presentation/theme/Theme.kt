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
    secondary = Color(0xFF313131),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFAFAFAF),
    tertiary = Color.White,
    surface = Color.Black,
    background = Color(0xFF1F1F1F),
    surfaceContainer = Color(0xFF282828),
    onTertiaryFixedVariant = Color.Yellow
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = ColorLightBlue,
    primaryContainer = ColorLightBlue,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFABABAB),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF656565),
    tertiary = Color.Black,
    surface = Color.White,
    background = Color.White,
    surfaceContainer = Color(0xFFE5E5E5),
    onTertiaryFixedVariant = Color(0xFCB7AE00)
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