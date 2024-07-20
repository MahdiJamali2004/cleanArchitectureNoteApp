package com.example.note.feature_note.presentation.note_screen

import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.util.NoteOrder
import com.example.note.feature_note.domain.util.OrderType
import java.time.LocalDate

data class NoteState (
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.DayAdded(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)