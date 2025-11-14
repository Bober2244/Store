package dev.bober.store.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.DataStoreManager
import dev.bober.store.utils.Constants
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
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

    fun saveFirstLaunch() {
        viewModelScope.launch {
            dataStore.saveBoolean(Constants.IS_FIRST_LAUNCH, false)
        }
    }
}