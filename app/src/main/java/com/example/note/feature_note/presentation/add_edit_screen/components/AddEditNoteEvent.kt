package com.example.note.feature_note.presentation.add_edit_screen.components

import androidx.compose.ui.focus.FocusState
import com.example.note.feature_note.domain.model.Note

sealed class AddEditNoteEvent {
    data class ColorChange(val color : Int) : AddEditNoteEvent()
    data class TitleFocus (val focusState: FocusState) : AddEditNoteEvent()
    data class ContentFocus (val focusState: FocusState) : AddEditNoteEvent()
    data class TitleChange (val text : String) : AddEditNoteEvent()
    data class ContentChange (val text : String) : AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}