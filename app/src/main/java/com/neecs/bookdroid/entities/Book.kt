package com.neecs.bookdroid.entities

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Int,
    val title: String,
    val description : String,
)
