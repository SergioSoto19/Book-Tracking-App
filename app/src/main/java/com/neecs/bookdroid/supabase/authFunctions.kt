package com.neecs.bookdroid.supabase

import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject


suspend fun registerUser(email: String, password: String, displayName: String) {
    try {

        val metadata = buildJsonObject {
            put("display_name", JsonPrimitive(displayName))
        }


        val user = supabase.auth.admin.createUserWithEmail {
            this.email = email
            this.password = password
            this.userMetadata = metadata
        }
        println("User created successfully with UID: ${user.id}")
    } catch (e: Exception) {
        println("Error creating user: ${e.message}")
    }
}