package dev.bober.store.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bober.store.data.dto.auth.LoginBodyDto
import dev.bober.store.data.dto.auth.RegisterBodyDto
import dev.bober.store.data.repository.OnboardingRepository
import dev.bober.store.data.utils.DataStoreManager
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.SendingState
import dev.bober.store.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val repository: OnboardingRepository,
    private val dataStore: DataStoreManager,
) : ViewModel() {
    private val _loginState = MutableStateFlow(SendingState.NONE)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(SendingState.NONE)
    val registerState = _registerState.asStateFlow()

    private val _confirmState = MutableStateFlow(SendingState.NONE)
    val confirmState = _confirmState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.emit(SendingState.LOADING)
            when (val res = repository.login(email = email, password = password)) {
                is Resource.Error -> _loginState.emit(SendingState.ERROR)
                is Resource.Loading -> _loginState.emit(SendingState.LOADING)
                is Resource.Success -> {
                    dataStore.saveString(
                        key = Constants.ACCESS_TOKEN,
                        value = res.data.accessToken
                    )
                    _loginState.emit(SendingState.SUCCESS)
                }
            }
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerState.emit(SendingState.LOADING)
            when (
                repository.register(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    passwordConfirmation = passwordConfirmation
                )
            ) {
                is Resource.Error -> _registerState.emit(SendingState.ERROR)
                is Resource.Loading -> _registerState.emit(SendingState.LOADING)
                is Resource.Success -> _registerState.emit(SendingState.SUCCESS)
            }
        }
    }

    fun confirmRegistration(token: String) {
        viewModelScope.launch {
            _confirmState.emit(SendingState.LOADING)
            when (repository.confirmRegistration(token)) {
                is Resource.Error -> _confirmState.emit(SendingState.ERROR)
                is Resource.Loading -> _confirmState.emit(SendingState.LOADING)
                is Resource.Success -> _confirmState.emit(SendingState.SUCCESS)
            }
        }
    }

    fun resendConfirmation(email: String) {
        viewModelScope.launch {
            repository.resendConfirmation(email)
        }
    }
}