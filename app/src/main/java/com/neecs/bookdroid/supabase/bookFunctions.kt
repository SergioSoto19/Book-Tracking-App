package com.neecs.bookdroid.supabase

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

suspend fun saveBook(title: String, image: String) {
    val currentUserId = supabase.auth.retrieveUserForCurrentSession(updateSession = true)?.id
        ?: throw IllegalStateException("No se pudo recuperar el userId para la sesión actual.")

    supabase.from("book").insert(
        mapOf(
            "title" to title,
            "image" to image,
            "user_id" to currentUserId // Usar el `currentUserId` recuperado
        )
    )
}


