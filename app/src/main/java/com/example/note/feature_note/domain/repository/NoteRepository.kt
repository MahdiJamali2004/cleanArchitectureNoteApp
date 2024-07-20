package com.example.note.feature_note.domain.repository

import com.example.note.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes() :Flow<List<Note>>

    suspend fun deleteNote(note: Note)

    suspend fun insertNote(note: Note)

    suspend fun getNote(id :Int) :Note?
}