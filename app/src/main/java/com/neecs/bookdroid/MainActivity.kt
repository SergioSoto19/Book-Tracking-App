package com.neecs.bookdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neecs.bookdroid.ui.composables.LoginScreen
import com.neecs.bookdroid.ui.theme.BookdroidTheme
import com.neecs.bookdroid.ui.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BookdroidTheme(darkTheme = true) {
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    // Pantalla de Login
                    composable("login") {
                        LoginScreen(
                            uiState = loginViewModel.uiState.collectAsState().value,
                            onUsernameOrEmailChange = loginViewModel::onUsernameOrEmailChange,
                            onPasswordChange = loginViewModel::onPasswordChange,
                            onForgotPassword = {
                                println("Forgot password clicked")
                            },
                            onLogin = { email, password -> // Proveer email y password
                                loginViewModel.login(
                                    onSuccess = {
                                        println("Login successful!")
                                        navController.navigate("home")
                                    },
                                    onError = { errorMessage ->
                                        println("Login failed: $errorMessage")
                                    }
                                )
                            },
                            onRegister = {
                                println("Registration flow not implemented yet.")
                            }
                        )
                    }


                    // Pantalla de Inicio (Home)
                    composable("home") {
                        HomeScreen(
                            onLogout = {
                                navController.popBackStack("login", false)
                            },
                            loginViewModel = loginViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    loginViewModel: LoginViewModel
) {
    var currentUserEmail by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Welcome to Home Screen!")

            // Botón para obtener el usuario actual
            Button(
                onClick = {
                    loginViewModel.retrieveCurrentUser(
                        onResult = { userEmail ->
                            currentUserEmail = userEmail
                            message = if (userEmail != null) {
                                "Current user email: $userEmail"
                            } else {
                                "No user is currently logged in."
                            }
                        },
                        onError = { error ->
                            message = "Failed to retrieve user: $error"
                        }
                    )
                }
            ) {
                Text("Get Current User")
            }

            // Mostrar correo del usuario actual o mensaje de error
            Text(text = message)

            // Botón para cerrar sesión
            Button(
                onClick = {
                    loginViewModel.logout(
                        onSuccess = {
                            message = "Logout successful!"
                            onLogout()
                        },
                        onError = { error ->
                            message = "Logout failed: $error"
                        }
                    )
                }
            ) {
                Text("Logout")
            }
        }
    }
}
