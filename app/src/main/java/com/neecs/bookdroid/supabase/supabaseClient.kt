package com.neecs.bookdroid.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

val supabase = createSupabaseClient(
    supabaseUrl = "https://hrwuigalxnecnfzdtrzn.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imhyd3VpZ2FseG5lY25memR0cnpuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzMwMzA2OTYsImV4cCI6MjA0ODYwNjY5Nn0.IhZv3giYIp3FvV1ZeZBTcT42EHzQHswAXsR6h57NYBo"
) {
    install(Auth)
    install(Postgrest)
    defaultSerializer = KotlinXSerializer(Json)
}

