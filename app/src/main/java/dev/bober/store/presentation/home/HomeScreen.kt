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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.canopas.lib.showcase.component.rememberIntroShowcaseState
import dev.bober.store.R
import dev.bober.store.domain.CategoryModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
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
                    title = {
                        Text(
                            text = "Магазин приложений",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        //TODO: profile icon
                    },
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
                                text = "RuStore",
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
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {
                        //TODO: searching
                    },
                    shape = RoundedCornerShape(18.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    placeholder = {
                        Text(text = "Найти приложение или игру")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .introShowCaseTarget(
                            index = 0,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = Color(0xFF1C0A00),
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
                                    /*Spacer(modifier = Modifier.height(10.dp))
                                    Icon(
                                        painterResource(id = R.drawable.right_arrow),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .align(Alignment.End),
                                        tint = Color.White
                                    )*/
                                }
                            }
                        )
                )

                val categories by viewModel.categories.collectAsState()
                var selectedCategory by remember { mutableStateOf<CategoryModel?>(null) }
                LazyRow(
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
                        items = categories
                    ) {
                        AssistChip(
                            onClick = {
                                selectedCategory = if (selectedCategory == it)
                                    null else it
                            },
                            label = {
                                Text(
                                    text = it.name
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (selectedCategory == it) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(20.dp),
                            border = null
                        )
                    }
                }

                val apps by viewModel.apps.collectAsState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(items = apps){
                        OutlinedCard (
                            onClick = {
                                //TODO: open
                            },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 0.dp,
                                focusedElevation = 8.dp,
                                hoveredElevation = 8.dp,
                                disabledElevation = 8.dp
                            ),
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(
                                        text = it.name,
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
                                            text = it.rating.toString()
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
                                            text = it.category
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}