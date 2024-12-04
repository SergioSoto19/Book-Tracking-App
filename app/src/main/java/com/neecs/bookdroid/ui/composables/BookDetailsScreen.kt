package com.neecs.bookdroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.neecs.bookdroid.entities.BookDto



@Composable
fun BookDetailsScreen(book: BookDto, onAddToList: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del libro
        Image(
            painter = rememberImagePainter(book.coverUrl),
            contentDescription = book.title,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título del libro
        Text(
            text = book.title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar a la lista
        Button(
            onClick = onAddToList,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Agregar a mi lista")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewBookDetailsScreen() {
    // Proporcionamos un ejemplo de BookDto
  //  val sampleBook = BookDto(
    //    id = "1",
      //  title = "Libro de Ejemplo",
        //coverUrl = "https://example.com/cover.jpg"
    //)

    //BookDetailsScreen(
      //  book = sampleBook,
       // onAddToList = { /* Lógica para agregar a la lista */ }
    //)
//}