package com.neecs.bookdroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.neecs.bookdroid.entities.BookDto

class SharedViewModel : ViewModel() {
    var selectedBook: BookDto? = null
        private set

    fun setSelectedBook(book: BookDto) {
        selectedBook = book
    }
}