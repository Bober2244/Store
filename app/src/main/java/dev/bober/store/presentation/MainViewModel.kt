package dev.bober.store.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.DataStoreManager
import dev.bober.store.di.dataModule
import dev.bober.store.presentation.navigation.destinations.OnboardingRoute
import dev.bober.store.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val dataStore: DataStoreManager
): ViewModel() {
    val isFirstLaunch = dataStore.getBooleanFlow(Constants.IS_FIRST_LAUNCH)
        .map {
            it ?: true
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed()
        )

    val bottomBarHiddenRoutes = listOf(
        OnboardingRoute::class.qualifiedName
    )
}