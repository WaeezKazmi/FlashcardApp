package com.example.flashcardapp

import android.app.Application
import com.example.flashcardapp.data.FlashcardDatabase
import com.example.flashcardapp.data.FlashcardRepository

class FlashcardApp : Application() {
    val database by lazy { FlashcardDatabase.getDatabase(this) }
    val repository by lazy { FlashcardRepository(database.flashcardDao()) }
}