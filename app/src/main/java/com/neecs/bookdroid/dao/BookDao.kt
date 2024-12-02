package com.neecs.bookdroid.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neecs.bookdroid.entities.Book

@Dao
interface BookDao {
    // Insertar un libro
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    // Obtener todos los libros
    @Query("SELECT * FROM Book")
    fun getAllBooks(): LiveData<List<Book>>

    // Eliminar un libro
    @Delete
    suspend fun delete(book: Book)
}