package com.neecs.bookdroid.data.model

data class LoginUiState(
    val firstName: String = "",         // Nombre
    val lastName: String = "",          // Apellido
    val usernameOrEmail: String = "",   // Correo electrónico o nombre de usuario
    val password: String = "",          // Contraseña
    val isLoading: Boolean = false      // Estado de carga
)