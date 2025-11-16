package dev.bober.store.presentation.home.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.domain.SendingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppDetailsViewModel(
    private val appRepository: AppsRepository,
): ViewModel() {
    val mutableDownloadingState = MutableStateFlow(SendingState.NONE)
    val downloadingState = mutableDownloadingState.asStateFlow()

    fun downloadApp(context: Context, appId: String, fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableDownloadingState.emit(SendingState.LOADING)
            appRepository.downloadApp(
                context = context,
                appId = appId,
                fileName = fileName
            )
        }
    }
}