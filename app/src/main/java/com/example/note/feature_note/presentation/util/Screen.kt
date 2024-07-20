package com.example.note.feature_note.presentation.util

sealed class Screen( val route : String) {
    object NoteScreen : Screen("note_screen")
    object AddEditScreen : Screen("add_edit_screen")
}