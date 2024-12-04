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

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neecs.bookdroid.network.ApiService
import com.neecs.bookdroid.network.RetrofitClient.apiService
import com.neecs.bookdroid.ui.composables.HomeScreen
import com.neecs.bookdroid.ui.composables.RegisterScreen
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel
import com.neecs.bookdroid.ui.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController() // Controlador de navegación

            BookdroidTheme(darkTheme = true) {

                // Configuración del NavHost y las pantallas de la app
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
                                // Aquí defines la navegación o acción para "Registrarse"
                                navController.navigate("register")
                            },
                        )
                    }


                    // Pantalla de Inicio (Home)
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

                   // composable("bookDetails/{bookId}") { backStackEntry ->
                     //   val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
                       // val book = getBookById(bookId)  // Recuperar el libro usando el ID (este es un ejemplo)

                        //BookDetailsScreen(
                          //  book = book,
                            //onAddToList = { /* Agregar a la lista */ }
                        //)
                    //}

                    // pantalla de detalles del libro

                    // Pantalla de contraseña olvidada

                }
            }
        }
    }
}

