package com.neecs.bookdroid.ui.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.neecs.bookdroid.data.model.LoginUiState


@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onUsernameOrEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Definimos las referencias de los elementos
        val (icon, usernameField, passwordField, forgotPassword, register, loginButton) = createRefs()

        // Ícono centrado en la parte superior
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "App Logo",
            modifier = Modifier
                .size(80.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, margin = 80.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            tint = MaterialTheme.colorScheme.primary
        )

        // Campo de texto para el nombre de usuario
        TextField(
            value = uiState.usernameOrEmail,
            onValueChange = onUsernameOrEmailChange,
            label = { Text("Nombre de usuario") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(usernameField) {
                    top.linkTo(icon.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Campo de texto para la contraseña
        TextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(passwordField) {
                    top.linkTo(usernameField.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            visualTransformation = PasswordVisualTransformation() // Oculta la contraseña
        )

        // Enlace "¿Olvidaste la contraseña?"
        Text(
            text = "¿Olvidaste la contraseña?",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = onForgotPassword)
                .padding(8.dp)
                .constrainAs(forgotPassword) {
                    top.linkTo(passwordField.bottom, margin = 35.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end) // Opcional, centra horizontalmente
                }
        )

        // Enlace "Registrarse"
        Text(
            text = "Registrarse",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = onRegister) // Llama al callback para navegar
                .padding(8.dp)
                .constrainAs(register) {
                    top.linkTo(forgotPassword.bottom, margin = 25.dp) // Colocado debajo de "¿Olvidaste la contraseña?"
                    start.linkTo(parent.start)
                    end.linkTo(parent.end) // Opcional, centra horizontalmente
                }
        )

        // Botón "Iniciar sesión"
        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(loginButton) {
                    top.linkTo(forgotPassword.bottom, margin = 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Iniciar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onFirstNameChange = {},
        onLastNameChange = {},
        onUsernameOrEmailChange = {},
        onPasswordChange = {},
        onLogin = {},
        onForgotPassword = {},
        onRegister = {}
    )
}