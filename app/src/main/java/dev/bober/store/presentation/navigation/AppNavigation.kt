package dev.bober.store.presentation.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.bober.store.domain.AppModel
import dev.bober.store.presentation.home.HomeScreen
import dev.bober.store.presentation.home.details.AppDetailsScreen
import dev.bober.store.presentation.navigation.destinations.AppDetailsRoute
import dev.bober.store.presentation.onboarding.OnboardingScreen
import dev.bober.store.presentation.navigation.destinations.CategoriesGraph
import dev.bober.store.presentation.navigation.destinations.CategoriesRoute
import dev.bober.store.presentation.navigation.destinations.HomeGraph
import dev.bober.store.presentation.navigation.destinations.HomeRoute
import dev.bober.store.presentation.navigation.destinations.OnboardingGraph
import dev.bober.store.presentation.navigation.destinations.OnboardingRoute
import dev.bober.store.presentation.utils.serializableNavType
import kotlin.reflect.typeOf

fun NavGraphBuilder.onboardingGraph(navController: NavController) {
    navigation<OnboardingGraph>(startDestination = OnboardingRoute) {
        composable<OnboardingRoute> {
            OnboardingScreen(
                onNextClick = {
                    navController.navigate(HomeGraph) {
                        popUpTo(HomeGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<HomeGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(
                onAppClick = {
                    navController.navigate(AppDetailsRoute(it))
                }
            )
        }

        composable<AppDetailsRoute>(
            typeMap = mapOf(
                typeOf<AppModel>() to serializableNavType<AppModel>()
            )
        ) {
            val appDetailsRoute = it.toRoute<AppDetailsRoute>()
            AppDetailsScreen(
                app = appDetailsRoute.app,
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavGraphBuilder.categoriesGraph(
    navController: NavController
) {
    navigation<CategoriesGraph>(startDestination = CategoriesRoute) {
        composable<CategoriesRoute> {
            //TODO: Categories screen
            Text(
                text = "Categories"
            )
        }
    }
}