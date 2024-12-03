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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neecs.bookdroid.ui.composables.LoginScreen
import com.neecs.bookdroid.ui.theme.BookdroidTheme
import com.neecs.bookdroid.ui.viewmodel.LoginViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neecs.bookdroid.network.ApiService
import com.neecs.bookdroid.network.RetrofitClient.apiService
import com.neecs.bookdroid.ui.composables.HomeScreen
import com.neecs.bookdroid.ui.composables.RegisterScreen
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel
import com.neecs.bookdroid.ui.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels() // Inicializa el ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginState = loginViewModel.uiState.collectAsState() // Obtenemos el estado actual de login

            val navController = rememberNavController() // Controlador de navegación

            BookdroidTheme(darkTheme = true) {

                // Configuración del NavHost y las pantallas de la app
                NavHost(
                    navController = navController,
                    startDestination = "login" // Pantalla inicial
                ) {
                    // Pantalla de inicio de sesión
                    composable("login") {
                        LoginScreen(
                            uiState = loginState.value, // Estado actual del Login
                            onFirstNameChange = loginViewModel::onFirstNameChange,
                            onLastNameChange = loginViewModel::onLastNameChange,
                            onUsernameOrEmailChange = loginViewModel::onUsernameOrEmailChange,
                            onPasswordChange = loginViewModel::onPasswordChange,
                            onForgotPassword = {
                                // Aquí defines la navegación o acción para "¿Olvidaste la contraseña?"
                                navController.navigate("forgotPassword")
                            },
                            onRegister = {
                                // Aquí defines la navegación o acción para "Registrarse"
                                navController.navigate("register")
                            },
                            onLogin = {
                                // Aquí se navega a la pantalla "home" cuando se inicia sesión
                                navController.navigate("home") {
                                    // Eliminar la pantalla de login para que no se pueda regresar

                                }
                            }
                        )
                    }

                    // Pantalla de registro (Ruta "register")
                    composable("register") {
                        RegisterScreen(
                            onRegister = {
                                // Lógica para registrar al usuario y navegar a la pantalla "home"
                                navController.navigate("home")
                            },
                            onBackToLogin = {
                                // Regresar a la pantalla de login
                                navController.popBackStack("login", false)
                            }
                        )
                    }

                    // Pantalla de inicio (Home)
                    composable("home") {
                        // Obtén el ViewModel aquí


                        val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(apiService))

                        HomeScreen(
                            viewModel = homeViewModel, // Pasa el ViewModel aquí
                            onNavigateToLibrary = {
                                // Lógica para navegar a la pantalla de biblioteca
                                navController.navigate("library")
                            },
                            onNavigateToExplore = {
                                // Lógica para navegar a la pantalla de explorar
                                navController.navigate("explore")
                            },
                            onNavigateToHome = {
                                // Si ya estás en la pantalla de inicio, no necesitas navegar
                                navController.navigate("home")
                            }
                        )
                    }


                    // Pantalla de contraseña olvidada

                }
            }
        }
    }
}