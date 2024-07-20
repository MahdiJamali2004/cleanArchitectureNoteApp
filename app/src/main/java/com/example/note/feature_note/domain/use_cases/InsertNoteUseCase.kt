package com.example.note.feature_note.domain.use_cases

import com.example.note.feature_note.domain.model.InvalidNoteException
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository

class InsertNoteUseCase(private val noteRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.content.isBlank()) {
            throw InvalidNoteException("Note must has content.")
        }
        noteRepository.insertNote(note)
    }
}