package com.neecs.bookdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.network.ApiService
import com.neecs.bookdroid.network.RetrofitClient.apiService

import com.neecs.bookdroid.ui.composables.BookDetailsScreen
import com.neecs.bookdroid.ui.composables.HomeScreen
import com.neecs.bookdroid.ui.composables.LoginScreen
import com.neecs.bookdroid.ui.composables.RegisterScreen
import com.neecs.bookdroid.ui.theme.BookdroidTheme
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel
import com.neecs.bookdroid.ui.viewmodel.HomeViewModelFactory
import com.neecs.bookdroid.ui.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val gson = Gson() // Inicializamos Gson

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
                            onLogin = { email, password ->
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
                                navController.navigate("register")
                            },
                        )
                    }

                    // Pantalla de Registro
                    composable("register") {
                        RegisterScreen(
                            onRegister = { email, password, firstName, lastName ->
                                println("Registering user: $email, $firstName $lastName")
                                navController.navigate("home")
                            },
                            onBackToLogin = {
                                navController.popBackStack("login", false)
                            }
                        )
                    }

                    // Pantalla Home
                    composable("home") {
                        val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(apiService))

                        HomeScreen(
                            viewModel = homeViewModel,
                            onNavigateToLibrary = { navController.navigate("library") },
                            onNavigateToExplore = { navController.navigate("explore") },
                            onNavigateToHome = { navController.navigate("home") },
                            onBookClick = { bookId ->
                                navController.navigate("bookDetails/$bookId") // Pasar `bookId` en la navegación
                            }
                        )
                    }

                    composable(
                        "bookDetails/{bookId}",
                        arguments = listOf(navArgument("bookId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
                        BookDetailsScreen(
                            bookId = bookId,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }


                    // Pantalla de Detalles del Libro
                    composable(
                        "bookDetails/{bookJson}",
                        arguments = listOf(navArgument("bookJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        // Deserializamos el objeto BookDto
                        val bookJson = backStackEntry.arguments?.getString("bookJson")
                        val book = gson.fromJson(bookJson, BookDto::class.java)

                        BookDetailsScreen(
                            book = book,
                            onAddToList = {
                                println("${book.title} añadido a la lista")
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
