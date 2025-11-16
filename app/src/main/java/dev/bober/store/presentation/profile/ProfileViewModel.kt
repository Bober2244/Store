package dev.bober.store.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.data.utils.DataStoreManager
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppDownloadHistoryModel
import dev.bober.store.domain.AppViewHistoryModel
import dev.bober.store.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: AppsRepository,
    dataStore: DataStoreManager,
): ViewModel() {
    val token = dataStore.getStringFlow(Constants.ACCESS_TOKEN)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    val mutableViewsHistory = MutableStateFlow<Resource<List<AppViewHistoryModel>>>(Resource.Loading())
    val viewsHistory = mutableViewsHistory.asStateFlow()

    val mutableDownloadsHistory = MutableStateFlow<Resource<List<AppDownloadHistoryModel>>>(Resource.Loading())
    val downloadsHistory = mutableDownloadsHistory.asStateFlow()

    fun getViewsHistory(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableViewsHistory.emit(Resource.Loading())
            when (val res = repository.getViewsHistory("Bearer $token")) {
                is Resource.Error -> mutableViewsHistory.emit(Resource.Error(res.code, res.error))
                is Resource.Loading -> mutableViewsHistory.emit(Resource.Loading())
                is Resource.Success -> mutableViewsHistory.emit(Resource.Success(res.data.data))
            }
        }
    }

    fun geDownloadsHistory(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableDownloadsHistory.emit(Resource.Loading())
            when (val res = repository.getDownloadsHistory("Bearer $token")) {
                is Resource.Error -> mutableDownloadsHistory.emit(Resource.Error(res.code, res.error))
                is Resource.Loading -> mutableDownloadsHistory.emit(Resource.Loading())
                is Resource.Success -> mutableDownloadsHistory.emit(Resource.Success(res.data.data))
            }
        }
    }
}