package com.neecs.bookdroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.neecs.bookdroid.R
import com.neecs.bookdroid.data.model.Book

@Composable
fun HomeScreen(
    onNavigateToLibrary: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToHome: () -> Unit,
    onSearch: (String) -> Unit, // Función para realizar la búsqueda
    books: List<Book> // Lista de libros obtenida de la API
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val (topBar, bookCatalog, bottomBar) = createRefs()

        // Barra Superior
        createGuidelineFromTop(0.0f).apply {
            TopBar(onSearch = onSearch, modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }

        // Lista de Libros
        BookCatalog(books = books, modifier = Modifier.constrainAs(bookCatalog) {
            top.linkTo(topBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(bottomBar.top)
        })

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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .background(Color(0xFF212121)),

        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Campo de búsqueda
        TextField(
            value = "",
            onValueChange = { query -> onSearch(query) },
            label = { Text("Buscar libros...") },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
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
fun BookCatalog(books: List<Book>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        items(books) { book ->
            BookItem(book = book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(100.dp)
            .clickable { /* Acción de seleccionar libro */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del libro
        Image(
            painter = painterResource(id = book.imageRes),
            contentDescription = book.title,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = book.title, color = Color.White, fontSize = 18.sp)
            Text(text = book.author, color = Color.Gray, fontSize = 14.sp)
        }
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
    // Aquí creamos datos de ejemplo para la vista previa
    val sampleBooks = listOf(
        Book("Book 1", "Author 1", R.drawable.book_image),
        Book("Book 2", "Author 2", R.drawable.book_image)
    )
    HomeScreen(
        onNavigateToLibrary = {},
        onNavigateToExplore = {},
        onNavigateToHome = {},
        onSearch = {},
        books = sampleBooks
    )
}
