package com.neecs.bookdroid.entities

data class BookDto(
    val title: String,        // Título del libro
    val cover_i: Int?         // ID de la portada (puede ser null)
) {
    // Genera la URL de la portada
    val coverUrl: String
        get() = cover_i?.let {
            "https://covers.openlibrary.org/b/id/$it-M.jpg"
        } ?: "https://via.placeholder.com/150?text=No+Cover"
}