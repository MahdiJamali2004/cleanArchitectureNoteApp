package com.example.note.feature_note.domain.use_cases

import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(private val noteRepository: NoteRepository) {

    suspend  operator fun invoke(id :Int) :Note? {
        return noteRepository.getNote(id)

    }
}