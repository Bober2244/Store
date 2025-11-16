package dev.bober.store.presentation.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.data.utils.DataStoreManager
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppModel
import dev.bober.store.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecommendationsViewModel(
    private val appsRepository: AppsRepository,
    dataStore: DataStoreManager,
): ViewModel() {
    val mutableRecommendations = MutableStateFlow<Resource<List<AppModel>>>(Resource.Loading())
    val recommendations = mutableRecommendations.asStateFlow()

    val appId = dataStore.getIntFLow(Constants.LAST_APP_ID)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 1
        )

    fun getRecommendations(appId: Int) {
        viewModelScope.launch {
            when (val res = appsRepository.getSimilar(appId)) {
                is Resource.Error -> mutableRecommendations.emit(Resource.Error(res.code, res.error))
                is Resource.Loading -> mutableRecommendations.emit(Resource.Loading())
                is Resource.Success -> mutableRecommendations.emit(Resource.Success(res.data.data))
            }
        }
    }
}