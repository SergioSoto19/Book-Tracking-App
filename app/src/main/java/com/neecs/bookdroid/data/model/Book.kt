package com.neecs.bookdroid.data.model

import com.neecs.bookdroid.R

data class Book(
    val title: String,
    val author: String,
    val imageRes: Int // Este sería el recurso de la imagen
)

val sampleBooks = listOf(
    Book("El Gran Gatsby", "F. Scott Fitzgerald", R.drawable.book_image,

   ),
)