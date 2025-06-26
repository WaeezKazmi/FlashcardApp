package com.example.flashcardapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Insert
    suspend fun insert(flashcard: Flashcard)

    @Update
    suspend fun update(flashcard: Flashcard)

    @Delete
    suspend fun delete(flashcard: Flashcard)

    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    @Query("SELECT DISTINCT subject FROM flashcards")
    fun getAllSubjects(): Flow<List<String>>

    @Query("SELECT * FROM flashcards WHERE subject = :subject")
    fun getFlashcardsBySubject(subject: String): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE id = :id")
    fun getFlashcardById(id: Int): Flow<Flashcard?>

    @Query("DELETE FROM flashcards WHERE subject = :subject")
    suspend fun deleteBySubject(subject: String)
}