package com.neecs.bookdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neecs.bookdroid.entities.Book
import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.network.RetrofitClient
import com.neecs.bookdroid.supabase.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { // Usamos el tema predeterminado
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BooksList()
                }
            }
        }
    }
}


@Composable
fun BooksList() {
    var books by remember { mutableStateOf<List<BookDto>>(listOf()) }

    // Realizamos el fetch desde la API usando Retrofit
    LaunchedEffect(Unit) {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.searchBooks("cien años de soledad")
            }
            books = response.docs // Guardamos los libros en el estado
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Mostramos la lista de libros
    LazyColumn {
        itemsIndexed(books) { index, book ->
            BookItem(book = book)
        }
    }
}

@Composable
fun BookItem(book: BookDto) {
    Text(
        text = "${book.title} - Cover: ${book.coverUrl}",
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}
