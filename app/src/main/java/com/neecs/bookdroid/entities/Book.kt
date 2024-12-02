package com.neecs.bookdroid.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
    val title: String,
    val description: String,
    val image: String
)
