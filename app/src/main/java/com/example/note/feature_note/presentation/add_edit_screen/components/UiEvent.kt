
package com.example.note.feature_note.presentation.add_edit_screen.components

sealed class UiEvent {
    data class ShowSnackbar(val message : String) : UiEvent()
    object SaveNote : UiEvent()
}