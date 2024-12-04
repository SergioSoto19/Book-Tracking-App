package com.neecs.bookdroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.neecs.bookdroid.R
import coil.compose.rememberImagePainter
import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.entities.BookResponse
import com.neecs.bookdroid.network.ApiService
import com.neecs.bookdroid.supabase.saveBook
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToLibrary: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToHome: () -> Unit,
    onBookClick: (BookDto) -> Unit // Callback acepta el objeto completo
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            onSearch = { query -> viewModel.searchBooks(query) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        BookCatalog(
            books = viewModel.books.collectAsState().value,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            onBookClick = onBookClick // Pasar el objeto completo al callback
        )

        BottomBar(
            onNavigateToLibrary = onNavigateToLibrary,
            onNavigateToExplore = onNavigateToExplore,
            onNavigateToHome = onNavigateToHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}





@Composable
fun TopBar(onSearch: (String) -> Unit, modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF212121)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Campo de búsqueda envuelto en un Box con margen
        Box(
            modifier = Modifier
                .weight(1f)  // Hace que el Box ocupe el espacio restante
                .padding(start = 18.dp, end = 16.dp) // Espaciado lateral para el campo de búsqueda
                .padding(top = 8.dp, bottom = 8.dp) // Margen superior e inferior para separar el TextField de los bordes de la barra
                .clip(RoundedCornerShape(30.dp))  // Bordes redondeados
                .background(Color.White)  // Fondo blanco para el TextField
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    onSearch(query)  // Llamamos a onSearch cuando el usuario escribe
                },
                label = { Text("Buscar libros...") },
                modifier = Modifier
                    .fillMaxWidth() // El TextField ocupa todo el ancho del Box
                    .padding(horizontal = 16.dp), // Espaciado interno en el TextField
                singleLine = true
            )
        }

        // Ícono de usuario redondeado
        IconButton(onClick = { /* Acción de perfil de usuario */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                tint = Color.White
            )
        }
    }
}


@Composable
fun BookCatalog(
    books: List<BookDto>,
    modifier: Modifier = Modifier,
    onBookClick: (BookDto) -> Unit // Callback espera un objeto de tipo `BookDto`
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(books) { book ->
            BookItem(book = book, onClick = { onBookClick(book) }) // Pasar el objeto `BookDto` al callback
        }
    }
}





@Composable
fun BookItem(book: BookDto, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(book.coverUrl),
            contentDescription = book.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(text = book.title, maxLines = 2, textAlign = TextAlign.Center)
    }
}





@Composable
fun BottomBar(
    onNavigateToLibrary: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF212121)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = { onNavigateToHome() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                modifier = Modifier.size(28.dp),
                tint = Color.White
            )
        }

        IconButton(onClick = { onNavigateToLibrary() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_library),
                contentDescription = "Biblioteca",
                modifier = Modifier.size(28.dp),
                tint = Color.White
            )
        }

        IconButton(onClick = { onNavigateToExplore() }) {
            Icon(
                painter = painterResource(id = R.drawable.c_explore1),
                contentDescription = "Explorar",
                modifier = Modifier.size(28.dp),
                tint = Color.White
            )
        }
    }
}
///o necesario

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    // Crear un ViewModel de prueba (puede ser un objeto Mock o un Fake ViewModel)
    val fakeViewModel = object : HomeViewModel(FakeApiService()) {
        // Simular el estado deseado para la vista previa
    }

    HomeScreen(
        viewModel = fakeViewModel,
        onNavigateToLibrary = { /* Acción de prueba */ },
        onNavigateToExplore = { /* Acción de prueba */ },
        onNavigateToHome = { /* Acción de prueba */ },
        onBookClick = { /* Acción de prueba para el click de un libro */ }
    )
}


// Fake ApiService para proporcionar datos simulados
class FakeApiService : ApiService {
    override suspend fun searchBooks(title: String): BookResponse {
        // Retornar datos simulados
        return BookResponse(listOf(/* Lista de libros simulados */))
    }
}

@Composable
fun BookDetailsScreen(
    book: BookDto,
    userId: String,
    onBack: () -> Unit
) {
    var saveMessage by remember { mutableStateOf("") } // Para mostrar mensajes de éxito o error
    var isSaving by remember { mutableStateOf(false) } // Para mostrar estado de guardado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mostrar imagen del libro
        Image(
            painter = rememberAsyncImagePainter(book.coverUrl),
            contentDescription = book.title,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Título del libro
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar el libro
        Button(
            onClick = {
                isSaving = true
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        saveBook(book.title, book.coverUrl) // Guardar libro
                        saveMessage = "Libro guardado exitosamente"
                    } catch (e: Exception) {
                        saveMessage = "Error al guardar el libro: ${e.message}"
                    } finally {
                        isSaving = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving // Desactivar botón mientras se guarda
        ) {
            Text(if (isSaving) "Guardando..." else "Guardar libro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de éxito o error
        if (saveMessage.isNotEmpty()) {
            Text(
                text = saveMessage,
                color = if (saveMessage.contains("exitosamente")) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}





