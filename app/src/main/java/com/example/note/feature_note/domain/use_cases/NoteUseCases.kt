package com.example.note.feature_note.domain.use_cases

data class NoteUseCases (
    val deleteNoteUseCase : DeleteNoteUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase
)
