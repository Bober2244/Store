package dev.bober.store.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.bober.store.domain.AppModel
import dev.bober.store.presentation.categories.CategoriesScreen
import dev.bober.store.presentation.home.HomeScreen
import dev.bober.store.presentation.home.details.AppDetailsScreen
import dev.bober.store.presentation.navigation.destinations.AppDetailsRoute
import dev.bober.store.presentation.navigation.destinations.CategoriesGraph
import dev.bober.store.presentation.navigation.destinations.CategoriesRoute
import dev.bober.store.presentation.navigation.destinations.HomeGraph
import dev.bober.store.presentation.navigation.destinations.HomeRoute
import dev.bober.store.presentation.navigation.destinations.LoginRoute
import dev.bober.store.presentation.navigation.destinations.OnboardingGraph
import dev.bober.store.presentation.navigation.destinations.OnboardingRoute
import dev.bober.store.presentation.navigation.destinations.ProfileRoute
import dev.bober.store.presentation.navigation.destinations.RecommendationsGraph
import dev.bober.store.presentation.navigation.destinations.RecommendationsRoute
import dev.bober.store.presentation.navigation.destinations.RegistrationRoure
import dev.bober.store.presentation.onboarding.LoginScreen
import dev.bober.store.presentation.onboarding.OnboardingScreen
import dev.bober.store.presentation.onboarding.RegistrationScreen
import dev.bober.store.presentation.profile.ProfileScreen
import dev.bober.store.presentation.recommendations.RecommendationsScreen
import dev.bober.store.presentation.utils.serializableNavType
import kotlin.reflect.typeOf

fun NavGraphBuilder.onboardingGraph(navController: NavController) {
    navigation<OnboardingGraph>(startDestination = OnboardingRoute) {
        composable<OnboardingRoute> {
            OnboardingScreen(
                onNextClick = {
                    navController.navigate(RegistrationRoure)
                }
            )
        }
        composable<RegistrationRoure> {
            RegistrationScreen(
                toLogin = {
                    navController.navigate(LoginRoute)
                }
            )
        }
        composable<LoginRoute> {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(HomeGraph) {
                        popUpTo(HomeGraph) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(RegistrationRoure)
                }
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<HomeGraph>(startDestination = HomeRoute()) {
        composable<HomeRoute> { stack ->
            val homeRoute = stack.toRoute<HomeRoute>()
            HomeScreen(
                selectedTag = homeRoute.selectedTag,
                onAppClick = {
                    navController.navigate(AppDetailsRoute(it))
                },
                onProfileClick = {
                    navController.navigate(ProfileRoute)
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

        composable<ProfileRoute>(
            typeMap = mapOf(
                typeOf<AppModel>() to serializableNavType<AppModel>()
            )
        ) {
            ProfileScreen(
                onAppClick = {
                    navController.navigate(AppDetailsRoute(it))
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
            CategoriesScreen(
                onCategoryClick = {
                    navController.navigate(HomeRoute(it))
                }
            )
        }
    }
}

fun NavGraphBuilder.recommendationsGraph(
    navController: NavController
) {
    navigation<RecommendationsGraph>(startDestination = RecommendationsRoute) {
        composable<RecommendationsRoute>(
            typeMap = mapOf(
                typeOf<AppModel>() to serializableNavType<AppModel>()
            )
        ) {
            RecommendationsScreen(
                onProfileClick = {
                    navController.navigate(ProfileRoute)
                },
                onAppClick = {
                    navController.navigate(AppDetailsRoute(it))
                }
            )
        }
    }
}