package dev.bober.store.presentation.home.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.bober.store.domain.AppModel
import dev.bober.store.presentation.utils.shimmerEffect
import dev.bober.store.utils.Constants
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppDetailsScreen(
    app: AppModel,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    viewModel: AppDetailsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    var showGallery by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf(0) }
    val token by viewModel.token.collectAsState()

    LaunchedEffect(Unit) {
        delay(1000)
        viewModel.saveAppId(app.appId)
        viewModel.appViewed(app.appId.toString(), token ?: "")
    }

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Детали приложения"
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = onClickBack
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBackIosNew,
                                    contentDescription = null,
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider()
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                var isExpanded by remember { mutableStateOf(false) }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            val request = ImageRequest.Builder(LocalContext.current)
                                .data("${Constants.BASE_URL}/api/images/${app.smallIconId}")
                                .crossfade(true)
                                .build()
                            SubcomposeAsyncImage(
                                model = request,
                                contentDescription = null
                            ) {
                                val painter = rememberAsyncImagePainter(request)
                                val painterState by painter.state.collectAsState()

                                when (painterState) {
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
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = "Разработчик: ${app.developerName}",
                                    color = Color.Gray,
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.secondary,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = app.category,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = app.ageRestriction,
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        HorizontalDivider()
                        Spacer(Modifier.height(10.dp))
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = "Описание",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Spacer(Modifier.height(6.dp))

                            Column(modifier = Modifier.animateContentSize()) {
                                Text(
                                    text = app.description,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                                )
                                Text(
                                    text = if (isExpanded) "Скрыть" else "Показать больше",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .clickable { isExpanded = !isExpanded }
                                        .padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        ) {
                            Text(
                                text = "Скриншоты",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Spacer(Modifier.height(6.dp))

                            val state = rememberPagerState { app.appCardScreenshotsIds?.size ?: 0 }
                            HorizontalPager(
                                state = state,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) { page ->
                                val request = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        "${Constants.BASE_URL}/api/images/${
                                            app.appCardScreenshotsIds?.get(
                                                page
                                            )
                                        }"
                                    )
                                    .crossfade(true)
                                    .build()
                                SubcomposeAsyncImage(
                                    model = request,
                                    contentDescription = null
                                ) {
                                    val painter = rememberAsyncImagePainter(request)
                                    val painterState by painter.state.collectAsState()

                                    when (painterState) {
                                        is AsyncImagePainter.State.Success -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedImageIndex = page
                                                        showGallery = true
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AsyncImage(
                                                    model = request,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .height(400.dp)
                                                )
                                            }
                                        }

                                        else -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(height = 400.dp, width = 180.dp)
                                                        .clip(RoundedCornerShape(20.dp))
                                                        .shimmerEffect()
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            HorizontalPageIndicator(
                                currentPage = state.currentPage,
                                totalPages = state.pageCount,
                                activeColor = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(Modifier.height(10.dp))
                            HorizontalDivider()
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                        ) {
                            var clickedPosition by remember { mutableStateOf<Int?>(null) }

                            val starFilled = Icons.Filled.Star
                            val starOutline = Icons.Outlined.StarOutline
                            Text(
                                text = "Рейтинг и отзывы",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                repeat(5) {
                                    Icon(
                                        imageVector = clickedPosition?.let { position ->
                                            if (it <= position)
                                                starFilled else starOutline
                                        } ?: starOutline,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onTertiaryFixedVariant,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .clickable {
                                                clickedPosition =
                                                    if (clickedPosition == it) null else it
                                            }
                                    )
                                }
                                Text(
                                    text = "${app.rating} из 5",
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                            }
                        }
                    }
                }
                TextButton(
                    onClick = {
                        viewModel.downloadApp(
                            context = context,
                            appId = app.appId.toString(),
                            fileName = app.name,
                            token = token ?: "",
                        )
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FileDownload,
                        contentDescription = null,
                    )
                    Text(
                        text = "Скачать"
                    )
                }
            }
        }

        if (showGallery) {
            FullScreenImageViewer(
                imageIds = app.appCardScreenshotsIds.orEmpty(),
                initialPage = selectedImageIndex,
                onDismiss = { showGallery = false }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FullScreenImageViewer(
    imageIds: List<Int>,
    initialPage: Int,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            val pagerState = rememberPagerState(initialPage = initialPage) {
                imageIds.size
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val request = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.BASE_URL}/api/images/${imageIds[page]}")
                    .crossfade(true)
                    .build()
                AsyncImage(
                    model = request,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
