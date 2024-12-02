package com.neecs.bookdroid.network

import com.neecs.bookdroid.entities.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String // Término de búsqueda
    ): BookResponse
}