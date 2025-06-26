package com.example.flashcardapp.data

import kotlinx.coroutines.flow.Flow

class FlashcardRepository(private val flashcardDao: FlashcardDao) {
    val allFlashcards: Flow<List<Flashcard>> = flashcardDao.getAllFlashcards()
    val allSubjects: Flow<List<String>> = flashcardDao.getAllSubjects()

    suspend fun insert(flashcard: Flashcard) = flashcardDao.insert(flashcard)
    suspend fun update(flashcard: Flashcard) = flashcardDao.update(flashcard)
    suspend fun delete(flashcard: Flashcard) = flashcardDao.delete(flashcard)
    suspend fun deleteBySubject(subject: String) = flashcardDao.deleteBySubject(subject)

    fun getFlashcardsBySubject(subject: String): Flow<List<Flashcard>> =
        flashcardDao.getFlashcardsBySubject(subject)

    fun getFlashcardById(id: Int): Flow<Flashcard?> =
        flashcardDao.getFlashcardById(id)
}