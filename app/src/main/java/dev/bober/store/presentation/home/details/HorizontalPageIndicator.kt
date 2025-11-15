package dev.bober.store.presentation.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Аналог HorizontalPagerIndicator из accompanist
 */
@Composable
fun HorizontalPageIndicator(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = Color.LightGray,
    dotSize: Dp = 8.dp
) {
    Row(
        modifier = modifier
            .wrapContentSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { page ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(if (page == currentPage) activeColor else inactiveColor)
            )
        }
    }
}