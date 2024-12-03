package com.neecs.bookdroid.supabase

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
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

suspend fun loginUser(email: String, password: String) {
    try {
        val user = supabase.auth.signInWith(Email){
            this.email = email
            this.password = password
        }
    } catch (e: Exception) {
        println("Error logging in: ${e.message}")
    }
}

suspend fun logoutUser() {
    try {
        supabase.auth.signOut()
    } catch (e: Exception) {
        println("Error logging out: ${e.message}")
    }
}

suspend fun retrieveUser(): UserInfo? {
    return try {
        // Recuperar el usuario de la sesión actual
        val user = supabase.auth.retrieveUserForCurrentSession(updateSession = true)
        println("User retrieved successfully: ${user?.id}")
        user
    } catch (e: Exception) {
        println("Error retrieving user: ${e.message}")
        null // Devolver null si ocurre un error
    }
}