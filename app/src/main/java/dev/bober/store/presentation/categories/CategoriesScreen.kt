package dev.bober.store.presentation.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.bober.store.data.utils.Resource
import dev.bober.store.presentation.utils.shimmerEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = koinViewModel(),
    onCategoryClick: (String) -> Unit = {}
) {
    val categoriesState by viewModel.categories.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = categoriesState) {
            is Resource.Success -> {
                val categories = state.data
                if (categories.isEmpty()) {
                    Text(
                        text = "Категории отсутствуют",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(categories) { category ->
                            Text(
                                text = category,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCategoryClick(category) }
                                    .padding(16.dp)
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(
                        onClick = {
                            viewModel.getCategories()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "Загрузить категории"
                        )
                    }
                }
            }
            is Resource.Loading -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(12) {
                        Box(
                            modifier = Modifier
                                .size(height = 30.dp, width = 200.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .shimmerEffect()
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
