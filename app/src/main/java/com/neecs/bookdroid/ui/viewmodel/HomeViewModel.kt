package com.neecs.bookdroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val apiService: ApiService) : ViewModel() {

    private val _books = MutableStateFlow<List<BookDto>>(emptyList())  // Lista de libros
    val books: StateFlow<List<BookDto>> get() = _books  // Exposición solo de lectura

    // Función que se llama desde el onSearch
    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                // Llamamos a la API con el query de búsqueda
                val response = apiService.searchBooks(query)
                _books.value = response.docs  // Actualizamos la lista de libros en _books
            } catch (e: Exception) {
                // Manejo de errores
                _books.value = emptyList()  // Si ocurre un error, no mostramos libros
            }
        }
    }
}