package com.neecs.bookdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neecs.bookdroid.ui.composables.LoginScreen
import com.neecs.bookdroid.ui.theme.BookdroidTheme
import com.neecs.bookdroid.ui.viewmodel.LoginViewModel

import androidx.compose.runtime.collectAsState


class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels() // Inicializa el ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginState = loginViewModel.uiState.collectAsState() // Obtenemos el estado actual de login

            val navController = rememberNavController() // Controlador de navegación

            BookdroidTheme {
                // Configuración del NavHost y las pantallas de la app
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    // Pantalla de inicio de sesión
                    composable("login") {
                        LoginScreen(
                            uiState = loginState.value, // Estado actual del Login
                            onFirstNameChange = loginViewModel::onFirstNameChange,
                            onLastNameChange = loginViewModel::onLastNameChange,
                            onUsernameOrEmailChange = loginViewModel::onUsernameOrEmailChange,
                            onPasswordChange = loginViewModel::onPasswordChange,
                            onLogin = {
                                // Navegar a "home" después de iniciar sesión
                                navController.navigate("home")
                            }
                        )
                    }

                    // Pantalla de inicio (Home)
                    composable("home") {
                        HomeScreen(
                            onLogout = {
                                // Regresar a la pantalla de inicio de sesión
                                navController.popBackStack("login", false)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    // Pantalla de inicio de sesión después de un inicio exitoso
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Contenido de la pantalla de inicio
        Button(
            onClick = onLogout,
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("Logout")
        }
    }
}