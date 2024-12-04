package com.neecs.bookdroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neecs.bookdroid.data.model.LoginUiState
import com.neecs.bookdroid.supabase.loginUser
import com.neecs.bookdroid.supabase.logoutUser
import com.neecs.bookdroid.supabase.registerUser
import com.neecs.bookdroid.supabase.retrieveUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameOrEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(usernameOrEmail = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun onFirstNameChange(value: String) {
        _uiState.value = _uiState.value.copy(firstName = value)
    }

    fun onLastNameChange(value: String) {
        _uiState.value = _uiState.value.copy(lastName = value)
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                // Intenta loguear al usuario
                val loginResult = loginUser(_uiState.value.usernameOrEmail, _uiState.value.password)

                // Si el login fue exitoso, verifica el usuario actual
                if (loginResult) {
                    val currentUser = retrieveUser()
                    if (currentUser != null) {
                        onSuccess()
                    } else {
                        onError("No se encontró un usuario después del inicio de sesión.")
                    }
                } else {
                    onError("Error al iniciar sesión. Revisa tus credenciales.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error durante el inicio de sesión.")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun register(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                registerUser(
                    email = _uiState.value.usernameOrEmail,
                    password = _uiState.value.password,
                    displayName = "${_uiState.value.firstName} ${_uiState.value.lastName}"
                )
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error durante el registro.")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                logoutUser()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error durante el cierre de sesión.")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun retrieveCurrentUser(onResult: (String?) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = retrieveUser()
                onResult(user?.email)
            } catch (e: Exception) {
                onError(e.message ?: "Error al obtener el usuario actual.")
            }
        }
    }
}
