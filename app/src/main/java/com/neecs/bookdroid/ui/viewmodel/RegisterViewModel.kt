package com.neecs.bookdroid.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neecs.bookdroid.data.model.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // Usamos un backing property para manejar el estado mutable
    private val _uiState = mutableStateOf(RegisterUiState())

    // Exponemos el estado de solo lectura
    val uiState: State<RegisterUiState> get() = _uiState

    // Función para manejar el cambio de los campos en el formulario
    fun onFieldChange(field: String, value: String) {
        _uiState.value = when (field) {
            "username" -> _uiState.value.copy(username = value)
            "firstName" -> _uiState.value.copy(firstName = value)
            "lastName" -> _uiState.value.copy(lastName = value)
            "password" -> _uiState.value.copy(password = value)
            "confirmPassword" -> _uiState.value.copy(confirmPassword = value)
            else -> _uiState.value
        }
    }

    // Función para simular el registro
    fun register(onSuccess: () -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Simulamos el registro
        viewModelScope.launch {
            delay(2000) // Simulamos un tiempo de respuesta
            _uiState.value = _uiState.value.copy(isLoading = false)
            onSuccess()  // Llamamos a la función de éxito al finalizar
        }
    }
}