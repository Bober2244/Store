package dev.bober.store.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.data.utils.DataStoreManager
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.AppModel
import dev.bober.store.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStore: DataStoreManager,
    private val appsRepository: AppsRepository,
) : ViewModel() {
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

    val mutableApps = MutableStateFlow<Resource<List<AppModel>>>(Resource.Loading())
    val apps = mutableApps.asStateFlow()

    val mutableTags = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val tags = mutableTags.asStateFlow()

    fun getAppsList(
        tag: String? = null,
        filter: String? = null,
        search: String? = null,
        sort: String? = null,
        order: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableApps.emit(Resource.Loading())
            when (val res = appsRepository.getAppsList(
                tag = tag,
                filter = filter,
                search = search,
                sort = sort,
                order = order
            )) {
                is Resource.Error -> {
                    mutableApps.emit(Resource.Error(res.code, res.error))
                }
                is Resource.Loading -> mutableApps.emit(Resource.Loading())
                is Resource.Success -> mutableApps.emit(Resource.Success(res.data.data))
            }
        }
    }

    fun getTags() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val res = appsRepository.getTags()) {
                is Resource.Error -> {
                    mutableTags.emit(Resource.Error(res.code, res.error))
                }
                is Resource.Loading -> mutableTags.emit(Resource.Loading())
                is Resource.Success -> mutableTags.emit(Resource.Success(res.data.data))
            }
        }
    }
}