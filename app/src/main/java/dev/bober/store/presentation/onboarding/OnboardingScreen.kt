package dev.bober.store.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bober.store.presentation.theme.ColorLightBlue

@Composable
fun OnboardingScreen(
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = ColorLightBlue,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Storefront,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text = "Добро пожаловать в RuStore!",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )
                Text(
                    text = "Откройте для себя мир российских приложений",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = null
                )
                Text(
                    text = "Исследуйте мир приложений",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Найдите эксклюзивные российские приложение и игры, доступные только здесь.",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
        }

        ElevatedButton(
            onClick = onNextClick,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 0.dp,
                focusedElevation = 8.dp,
                hoveredElevation = 8.dp,
                disabledElevation = 8.dp
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                text = "Начать"
            )
        }
    }
}