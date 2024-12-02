package com.neecs.bookdroid

import android.app.Application
import com.neecs.bookdroid.database.AppDatabase

class MyApplication : Application() {

    // Inicializa la base de datos con lazy para cargarla solo cuando se necesita
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}