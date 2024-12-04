package com.neecs.bookdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.network.RetrofitClient.apiService
import com.neecs.bookdroid.supabase.saveBook
import com.neecs.bookdroid.ui.composables.BookDetailsScreen
import com.neecs.bookdroid.ui.composables.HomeScreen
import com.neecs.bookdroid.ui.composables.LoginScreen
import com.neecs.bookdroid.ui.composables.RegisterScreen
import com.neecs.bookdroid.ui.theme.BookdroidTheme
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel
import com.neecs.bookdroid.ui.viewmodel.HomeViewModelFactory
import com.neecs.bookdroid.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val gson = Gson() // Inicializamos Gson para serialización/deserialización

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
                            onForgotPassword = { println("Forgot password clicked") },
                            onLogin = { email, password ->
                                loginViewModel.login(
                                    onSuccess = { navController.navigate("home") },
                                    onError = { errorMessage -> println("Login failed: $errorMessage") }
                                )
                            },
                            onRegister = { navController.navigate("register") }
                        )
                    }

                    // Pantalla de Registro
                    composable("register") {
                        RegisterScreen(
                            onRegister = { email, password, firstName, lastName ->
                                println("Registering user: $email, $firstName $lastName")
                                navController.navigate("home")
                            },
                            onBackToLogin = { navController.popBackStack("login", false) }
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
                            onBookClick = { book ->
                                // Serializar el libro como JSON para enviarlo
                                val bookJson = gson.toJson(book)
                                navController.navigate("bookDetails/$bookJson")
                            }
                        )
                    }

                    composable(
                        "bookDetails/{bookJson}",
                        arguments = listOf(navArgument("bookJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val bookJson = backStackEntry.arguments?.getString("bookJson")
                        val book = try {
                            gson.fromJson(bookJson, BookDto::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }

                        if (book != null) {
                            BookDetailsScreen(
                                book = book,
                                onAddToList = {
                                    kotlinx.coroutines.GlobalScope.launch {
                                        try {
                                            saveBook(book.title, book.coverUrl) // Reemplaza con el userId real
                                            println("${book.title} guardado exitosamente")
                                        } catch (e: Exception) {
                                            println("Error al guardar el libro: ${e.message}")
                                        }
                                    }
                                },
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        } else {
                            // Si `book` es nulo, regresa al usuario a la pantalla anterior
                            println("Error: El libro no es válido o el JSON está corrupto")
                            navController.popBackStack()
                        }
                    }

                }
            }
        }
    }
}
