package com.example.note.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.note.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

    @Upsert
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend fun getNote(id: Int): Note?

}