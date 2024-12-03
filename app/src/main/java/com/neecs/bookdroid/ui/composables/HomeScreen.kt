package com.neecs.bookdroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.neecs.bookdroid.R
import coil.compose.rememberImagePainter

import com.neecs.bookdroid.entities.BookDto
import com.neecs.bookdroid.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToLibrary: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToHome: () -> Unit

) {
    val books by viewModel.books.collectAsState()  // Estado con la lista de libros

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val (topBar, bookCatalog, bottomBar) = createRefs()

        // Barra Superior
        TopBar(
            onSearch = { query ->
                viewModel.searchBooks(query)  // Llamamos al ViewModel para buscar libros
            },
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Catálogo de Libros
        BookCatalog(
            books = books,
            modifier = Modifier.constrainAs(bookCatalog) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomBar.top)
            }
        )

        // Barra Inferior de Navegación
        BottomBar(
            onNavigateToLibrary = onNavigateToLibrary,
            onNavigateToExplore = onNavigateToExplore,
            onNavigateToHome = onNavigateToHome,
            modifier = Modifier.constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
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
        // Campo de búsqueda
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onSearch(query)  // Llamamos a onSearch cuando el usuario escribe
            },
            label = { Text("Buscar libros...") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 16.dp),
            singleLine = true
        )

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
            .clickable { /* Acción de seleccionar libro */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del libro desde la URL
        Image(
            painter = rememberImagePainter(book.coverUrl),  // Carga la imagen con Coil
            contentDescription = book.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.title,
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
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
            .padding(vertical = 8.dp)
            .background(Color(0xFF212121)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = { onNavigateToHome() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                modifier = Modifier.size(36.dp),
                tint = Color.White
            )
        }

        IconButton(onClick = { onNavigateToLibrary() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_library),
                contentDescription = "Biblioteca",
                modifier = Modifier.size(36.dp),
                tint = Color.White
            )
        }

        IconButton(onClick = { onNavigateToExplore() }) {
            Icon(
                painter = painterResource(id = R.drawable.c_explore1),
                contentDescription = "Explorar",
                modifier = Modifier.size(36.dp),
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {



}
