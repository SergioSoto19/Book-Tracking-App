package com.neecs.bookdroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class HomeViewModel(private val apiService: ApiService) : ViewModel() {

    // Lista de libros disponibles
    private val _books = MutableStateFlow<List<BookDto>>(emptyList())
    val books: StateFlow<List<BookDto>> get() = _books

    // Error en la búsqueda (si ocurre)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Realiza la búsqueda de libros según el título proporcionado por el usuario.
     */
    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                // Llama a la API para buscar libros
                val response = apiService.searchBooks(query)
                _books.value = response.docs
                _error.value = null // Reinicia cualquier error previo
            } catch (e: Exception) {
                // Manejo de errores: limpia la lista y almacena el error
                _books.value = emptyList()
                _error.value = "Error al buscar libros: ${e.message}"
            }
        }
    }

    /**
     * Obtiene un libro específico de la lista de libros cargados.
     *
     * @param title El título del libro que se desea buscar.
     * @return El `BookDto` correspondiente o `null` si no se encuentra.
     */
    fun getBookByTitle(title: String): BookDto? {
        return _books.value.find { it.title == title }
    }
}
