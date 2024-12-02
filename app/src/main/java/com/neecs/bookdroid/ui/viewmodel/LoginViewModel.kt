package com.neecs.bookdroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.neecs.bookdroid.data.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onFirstNameChange(value: String) {
        _uiState.value = _uiState.value.copy(firstName = value)
    }

    fun onLastNameChange(value: String) {
        _uiState.value = _uiState.value.copy(lastName = value)
    }

    fun onUsernameOrEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(usernameOrEmail = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login(onSuccess: () -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Simulamos la autenticación
        // Aquí puedes agregar la lógica real de autenticación
        onSuccess()
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}