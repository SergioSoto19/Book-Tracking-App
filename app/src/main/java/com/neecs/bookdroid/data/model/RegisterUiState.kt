package com.neecs.bookdroid.data.model

data class RegisterUiState(
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
