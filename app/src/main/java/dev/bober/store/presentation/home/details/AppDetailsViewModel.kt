package dev.bober.store.presentation.home.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.data.utils.DataStoreManager
import dev.bober.store.domain.SendingState
import dev.bober.store.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppDetailsViewModel(
    private val appRepository: AppsRepository,
    private val dataStore: DataStoreManager,
): ViewModel() {
    val mutableDownloadingState = MutableStateFlow(SendingState.NONE)
    val downloadingState = mutableDownloadingState.asStateFlow()

    val token = dataStore.getStringFlow(Constants.ACCESS_TOKEN)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    fun saveAppId(appId: Int) {
        viewModelScope.launch {
            dataStore.saveInt(Constants.LAST_APP_ID, appId)
        }
    }

    fun downloadApp(context: Context, appId: String, fileName: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableDownloadingState.emit(SendingState.LOADING)
            appRepository.downloadApp(
                context = context,
                appId = appId,
                fileName = fileName,
                token = "Bearer $token",
            )
        }
    }

    fun appViewed(
        appId: String,
        token: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.appViewed(
                appId = appId,
                token = "Bearer $token"
            )
        }
    }
}