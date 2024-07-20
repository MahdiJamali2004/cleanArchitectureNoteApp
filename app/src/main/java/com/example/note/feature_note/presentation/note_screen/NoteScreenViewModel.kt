package com.example.note.feature_note.presentation.note_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.use_cases.NoteUseCases
import com.example.note.feature_note.domain.util.NoteOrder
import com.example.note.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel
@Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNoteJob: Job? = null

    init {
        getNotes(NoteOrder.DayAdded(OrderType.Descending))
        println(state.value.notes.toString())
        Log.v("test","The note in init are ${state.value.notes.toString()}")
       noteUseCases.getNotesUseCase()
           .onEach {
               Log.v("test",it.toString())
           }
           .launchIn(viewModelScope)

    }

    fun onEvent(noteEvents: NoteEvents) {
        when (noteEvents) {
            is NoteEvents.Order -> {
                if (state.value.noteOrder::class == noteEvents.noteOrder.orderType::class &&
                    state.value.noteOrder.orderType == noteEvents.noteOrder.orderType
                )
                    return
                getNotes(noteEvents.noteOrder)

            }

            is NoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(noteEvents.note)
                    recentlyDeletedNote = noteEvents.note
                }

            }

            is NoteEvents.RestoreNote -> {

                viewModelScope.launch {
                    noteUseCases.insertNoteUseCase(recentlyDeletedNote!!)
                    recentlyDeletedNote = null
                }
            }

            is NoteEvents.ToggleOrderSection -> {
                _state.value =
                    state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }

    }

        fun getNotes(noteOrder: NoteOrder) {
            getNoteJob?.cancel()
            getNoteJob = noteUseCases.getNotesUseCase(noteOrder)
                .onEach { notes ->
                    _state.value = state.value.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }.launchIn(viewModelScope)

        }
}