package dev.bober.store.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppDownloadHistoryModel
import dev.bober.store.domain.AppModel
import dev.bober.store.domain.AppViewHistoryModel
import dev.bober.store.presentation.utils.shimmerEffect
import dev.bober.store.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinActivityViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ProfileScreen(
    onAppClick: (AppModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinActivityViewModel()
) {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()
    val tabTitles = listOf("История загрузок", "История просмотров")

    val token by viewModel.token.collectAsState()
    val viewsHistory by viewModel.viewsHistory.collectAsState()
    val downloadsHistory by viewModel.downloadsHistory.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yy 'в' HH:mm")

    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        SecondaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        pagerState.currentPage,
                        matchContentSize = false,
                    ),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        ) {
            tabTitles.forEachIndexed { index, titleResId ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = titleResId
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> HistoryList(
                    items = downloadsHistory,
                    onAppClick = onAppClick,
                    viewModel = viewModel,
                    token = token,
                    formatter = formatter
                )

                1 -> HistoryList(
                    items = viewsHistory,
                    onAppClick = onAppClick,
                    viewModel = viewModel,
                    token = token,
                    formatter = formatter,
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        viewModel.geDownloadsHistory(token ?: "")
        viewModel.getViewsHistory(token ?: "")
    }
}

@Composable
private fun HistoryList(
    items: Resource<List<AppDownloadHistoryModel>>,
    onAppClick: (AppModel) -> Unit,
    viewModel: ProfileViewModel,
    token: String?,
    formatter: DateTimeFormatter
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when (items) {
            is Resource.Error -> {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.geDownloadsHistory(token ?: "")
                                viewModel.getViewsHistory(token ?: "")
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

            is Resource.Success -> {
                items(items.data) { item ->
                    HistoryItem(
                        app = item,
                        onClick = {
                            onAppClick(it)
                        },
                        formatter = formatter
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryList(
    items: Resource<List<AppViewHistoryModel>>,
    onAppClick: (AppModel) -> Unit,
    viewModel: ProfileViewModel,
    token: String?,
    formatter: DateTimeFormatter,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when (items) {
            is Resource.Error -> {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.geDownloadsHistory(token ?: "")
                                viewModel.getViewsHistory(token ?: "")
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

            is Resource.Success -> {
                items(items.data) { item ->
                    HistoryItem(
                        app = item,
                        onClick = {
                            onAppClick(it)
                        },
                        formatter = formatter
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(
    app: AppDownloadHistoryModel,
    onClick: (AppModel) -> Unit,
    formatter: DateTimeFormatter,
) {
    OutlinedCard(
        onClick = {
            onClick(app.toAppModel())
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
                Text(
                    text = "${app.downloadedAt.format(formatter)}",
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun HistoryItem(
    app: AppViewHistoryModel,
    onClick: (AppModel) -> Unit,
    formatter: DateTimeFormatter
) {
    OutlinedCard(
        onClick = {
            onClick(app.toAppModel())
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
                Text(
                    text = "${app.viewedAt.format(formatter)}",
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    fontSize = 14.sp
                )
            }
        }
    }
}