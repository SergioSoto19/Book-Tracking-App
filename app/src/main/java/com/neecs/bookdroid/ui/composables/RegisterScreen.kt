package com.neecs.bookdroid.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun RegisterScreen(
    onRegister: (String, String, String, String) -> Unit, // Lógica para registrar al usuario
    onBackToLogin: () -> Unit // Lógica para volver a la pantalla de login
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // IDs para los elementos
        val (title, firstNameField, lastNameField, emailField, passwordField, registerButton, backButton) = createRefs()

        // Título
        Text(
            text = "Registrar",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Campo de nombre
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Nombre") },
            modifier = Modifier.constrainAs(firstNameField) {
                top.linkTo(title.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Campo de apellido
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") },
            modifier = Modifier.constrainAs(lastNameField) {
                top.linkTo(firstNameField.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Campo de correo electrónico
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.constrainAs(emailField) {
                top.linkTo(lastNameField.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Campo de contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.constrainAs(passwordField) {
                top.linkTo(emailField.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Botón para registrar
        Button(
            onClick = {
                onRegister(firstName, lastName, email, password)
            },
            modifier = Modifier.constrainAs(registerButton) {
                top.linkTo(passwordField.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text("Registrarse")
        }

        // Enlace para volver a la pantalla de login
        Text(
            text = "¿Ya tienes una cuenta? Inicia sesión",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = onBackToLogin)
                .constrainAs(backButton) {
                    top.linkTo(registerButton.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        onRegister = { firstName, lastName, email, password ->
            println("Registro: Nombre: $firstName, Apellido: $lastName, Email: $email, Contraseña: $password")
        },
        onBackToLogin = {
            println("Regresar al login")
        }
    )
}
