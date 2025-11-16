package dev.bober.store.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.bober.store.presentation.navigation.categoriesGraph
import dev.bober.store.presentation.navigation.destinations.AppDestinations
import dev.bober.store.presentation.navigation.destinations.HomeGraph
import dev.bober.store.presentation.navigation.destinations.OnboardingGraph
import dev.bober.store.presentation.navigation.homeGraph
import dev.bober.store.presentation.navigation.onboardingGraph
import dev.bober.store.presentation.navigation.recommendationsGraph
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel()
) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isFirstLaunch by viewModel.isFirstLaunch.collectAsState()

    val shouldShowBottomBar = currentRoute !in viewModel.bottomBarHiddenRoutes

    /*NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        },
        layoutType = if (shouldShowBottomBar) calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()) else NavigationSuiteType.None
    ) {

    }*/
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedContent(
                targetState = shouldShowBottomBar,
                transitionSpec = {
                    if (targetState) {
                        slideInVertically { fullHeight -> fullHeight } + fadeIn() togetherWith
                                slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                    } else {
                        slideInVertically { fullHeight -> fullHeight } + fadeIn() togetherWith
                                slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                    }.using(SizeTransform(clip = false))
                }
            ) { needToShow ->
                if (needToShow) {
                    NavigationBar {
                        AppDestinations.entries.forEach {
                            NavigationBarItem(
                                selected = currentDestination == it,
                                onClick = {
                                    currentDestination = it
                                    navController.navigate(it.graphRoute) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = it.icon,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = it.label
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    indicatorColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding),
            navController = navController,
            startDestination = if (isFirstLaunch) OnboardingGraph else HomeGraph
        ) {
            onboardingGraph(
                navController = navController
            )
            homeGraph(
                navController = navController
            )
            categoriesGraph(
                navController = navController
            )
            recommendationsGraph(
                navController = navController
            )
        }
    }
}
