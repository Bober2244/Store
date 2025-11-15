package dev.bober.store.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.repository.AppsRepository
import dev.bober.store.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val appsRepository: AppsRepository,
) : ViewModel() {

    private val mutableCategories = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val categories = mutableCategories.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableCategories.emit(Resource.Loading())
            when (val res = appsRepository.getTags()) {
                is Resource.Error -> mutableCategories.emit(Resource.Error(res.code, res.error))
                is Resource.Loading -> mutableCategories.emit(Resource.Loading())
                is Resource.Success -> mutableCategories.emit(Resource.Success(res.data.data))
            }
        }
    }
}