package dev.bober.store.presentation.recommendations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.bober.store.R
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppModel
import dev.bober.store.presentation.utils.shimmerEffect
import dev.bober.store.utils.Constants
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(
    onProfileClick: () -> Unit,
    onAppClick: (AppModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendationsViewModel = koinActivityViewModel()
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                title = {
                    Text(
                        text = "Рекомендации",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onProfileClick()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                expandedHeight = 24.dp
            )
        }
    ) { innerPadding ->

        val recommendations by viewModel.recommendations.collectAsState()
        val appId by viewModel.appId.collectAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 10.dp, end = 10.dp, top = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            when (recommendations) {
                is Resource.Success -> {
                    items(recommendations.data ?: emptyList()) { app ->
                        OutlinedCard(
                            onClick = {
                                onAppClick(app)
                            },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 0.dp,
                                focusedElevation = 8.dp,
                                hoveredElevation = 8.dp,
                                disabledElevation = 8.dp
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            colors = CardDefaults.outlinedCardColors(
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                val request = ImageRequest.Builder(LocalContext.current)
                                    .data("${Constants.BASE_URL}/api/images/${app.smallIconId}")
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .crossfade(true)
                                    .build()
                                SubcomposeAsyncImage(
                                    model = request,
                                    contentDescription = null
                                ) {
                                    val painter = rememberAsyncImagePainter(request)
                                    val state by painter.state.collectAsState()

                                    when (state) {
                                        is AsyncImagePainter.State.Success -> {
                                            AsyncImage(
                                                model = request,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(CircleShape)
                                            )
                                        }

                                        else -> {
                                            Box(
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(CircleShape)
                                                    .shimmerEffect()
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(
                                        text = app.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.StarBorder,
                                            contentDescription = null
                                        )
                                        Text(
                                            text = app.rating.toString()
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .size(2.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.tertiary)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(
                                            text = app.category
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TextButton(
                                onClick = {
                                    viewModel.getRecommendations(
                                        appId = appId ?: 1
                                    )
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            ) {
                                Text(
                                    text = "Загрузить приложения"
                                )
                            }
                        }
                    }
                }

                is Resource.Loading -> {
                    items(count = 8) {
                        OutlinedCard(
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 0.dp,
                                focusedElevation = 8.dp,
                                hoveredElevation = 8.dp,
                                disabledElevation = 8.dp
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .shimmerEffect()
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f)
                                            .height(30.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .shimmerEffect()
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(width = 120.dp, height = 20.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .shimmerEffect()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            delay(500)
            viewModel.getRecommendations(
                appId = appId ?: 1
            )
        }
    }
}