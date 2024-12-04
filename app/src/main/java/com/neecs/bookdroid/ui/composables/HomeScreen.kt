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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.neecs.bookdroid.R
import coil.compose.rememberImagePainter
import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.entities.BookResponse
import com.neecs.bookdroid.network.ApiService
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel



@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToLibrary: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Barra superior
        TopBar(
            onSearch = { query ->
                viewModel.searchBooks(query)  // Llamamos al ViewModel para buscar libros
            },
            modifier = Modifier
                .fillMaxWidth() // La barra superior ocupa todo el ancho
                .height(56.dp)  // Altura de la barra
        )

        // Catálogo de libros
        BookCatalog(
            books = viewModel.books.collectAsState().value,
            modifier = Modifier
                .weight(1f) // Ocupa el espacio restante
                .fillMaxWidth()

        )

        // Barra inferior
        BottomBar(
            onNavigateToLibrary = onNavigateToLibrary,
            onNavigateToExplore = onNavigateToExplore,
            onNavigateToHome = onNavigateToHome,
            modifier = Modifier
                .fillMaxWidth() // La barra inferior ocupa todo el ancho
                .height(56.dp) // Altura de la barra
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
fun BookCatalog(books: List<BookDto>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Tres columnas
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(books) { book ->
            BookItem(book = book)  // Muestra cada libro en la cuadrícula
        }
    }
}


@Composable
fun BookItem(book: BookDto) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                // Navegar a la pantalla de detalles del libro
              //  navController.navigate("bookDetails/${book.id}")  // Usamos el ID del libro para navegar
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del libro desde la URL
        Image(
            painter = rememberImagePainter(book.coverUrl),
            contentDescription = book.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
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
        onNavigateToHome = { /* Acción de prueba */ }
    )
}

// Fake ApiService para proporcionar datos simulados
class FakeApiService : ApiService {
    override suspend fun searchBooks(title: String): BookResponse {
        // Retornar datos simulados
        return BookResponse(listOf(/* Lista de libros simulados */))
    }
}
