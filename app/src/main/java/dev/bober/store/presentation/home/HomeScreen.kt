package dev.bober.store.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.canopas.lib.showcase.component.rememberIntroShowcaseState
import dev.bober.store.R
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppModel
import dev.bober.store.presentation.theme.ColorLightBlue
import dev.bober.store.presentation.utils.shimmerEffect
import dev.bober.store.utils.Constants
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAppClick: (AppModel) -> Unit,
    selectedTag: String?,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val introShowcaseState = rememberIntroShowcaseState()
    val showAppIntro by viewModel.isFirstLaunch.collectAsState()
    IntroShowcase(
        state = introShowcaseState,
        dismissOnClickOutside = true,
        showIntroShowCase = showAppIntro,
        onShowCaseCompleted = {
            viewModel.saveFirstLaunch()
        }
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
                            text = "Витрина",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        //TODO: profile icon
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    navigationIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(26.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.app_icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "VkStore",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    expandedHeight = 24.dp
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 10.dp, end = 10.dp, top = 6.dp)
            ) {
                var searchText by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    shape = CircleShape,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    placeholder = {
                        Text(text = "Найти приложение или игру")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .introShowCaseTarget(
                            index = 0,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = ColorLightBlue,
                                backgroundAlpha = 0.98f,
                                targetCircleColor = Color.White
                            ),
                            content = {
                                Column {
                                    Text(
                                        text = "Поисковая строка",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Необходима для поиска приложений",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        )
                )

                val tags by viewModel.tags.collectAsState()
                var currentTag by remember { mutableStateOf(selectedTag) }
                when (tags) {
                    is Resource.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .introShowCaseTarget(
                                    index = 1,
                                    style = ShowcaseStyle.Default.copy(
                                        backgroundColor = Color(0xFF7C99AC),
                                        backgroundAlpha = 0.98f,
                                        targetCircleColor = Color.White
                                    ),
                                    content = {
                                        Column {
                                            Text(
                                                text = "Выбор категорий",
                                                color = Color.White,
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Здесь можно фильтровать приложения по категориям",
                                                color = Color.White,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                )
                        ) {
                            items(
                                items = tags.data ?: emptyList()
                            ) {
                                AssistChip(
                                    onClick = {
                                        currentTag = if (currentTag == it)
                                            null else it
                                    },
                                    label = {
                                        Text(
                                            text = it
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = if (currentTag == it)
                                            MaterialTheme.colorScheme.secondaryContainer
                                        else
                                            MaterialTheme.colorScheme.secondary,
                                        labelColor = if (currentTag == it)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSecondary,
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = null
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        currentTag = null
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            TextButton(
                                onClick = {
                                    viewModel.getTags()
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            ) {
                                Text(
                                    text = "Згрузить теги"
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .shimmerEffect()
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))

                val apps by viewModel.apps.collectAsState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    when (apps) {
                        is Resource.Success -> {
                            itemsIndexed(items = apps.data ?: emptyList()) { index, app ->
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
                                    modifier = if (apps.data?.isNotEmpty() == true && index == 0) {
                                        Modifier
                                            .introShowCaseTarget(
                                                index = 2,
                                                style = ShowcaseStyle.Default.copy(
                                                    backgroundColor = Color(0xFF1C0A00),
                                                    backgroundAlpha = 0.98f,
                                                    targetCircleColor = Color.White
                                                ),
                                                content = {
                                                    Column {
                                                        Text(
                                                            text = "Список приложений",
                                                            color = Color.White,
                                                            fontSize = 24.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                        Text(
                                                            text = "Это список приложений, доступных для скачивания",
                                                            color = Color.White,
                                                            fontSize = 16.sp
                                                        )
                                                    }
                                                }
                                            )
                                    } else Modifier
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
                                            viewModel.getAppsList(
                                                tag = currentTag,
                                                search = searchText
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
                    viewModel.getAppsList(
                        tag = currentTag,
                        search = searchText,
                    )
                    viewModel.getTags()
                }
                LaunchedEffect(currentTag, searchText) {
                    viewModel.getAppsList(
                        tag = currentTag,
                        search = searchText,
                    )
                }
            }
        }
    }
}