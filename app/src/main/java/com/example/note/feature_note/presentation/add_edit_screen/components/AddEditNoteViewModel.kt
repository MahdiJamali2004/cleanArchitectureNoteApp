package com.example.note.feature_note.presentation.add_edit_screen.components

import android.util.Log
import androidx.annotation.MainThread
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.domain.model.InvalidNoteException
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentId: Int? = null

    private var _noteTitle = mutableStateOf(
        EditNoteState(
            hint = "Enter title ...",
            text = "",
            isHintVisible = true
        )
    )
    val noteTitle: State<EditNoteState> = _noteTitle

    private var _noteContent = mutableStateOf(
        EditNoteState(
            hint = "Enter something ...",
            text = "",
            isHintVisible = true
        )
    )
    val noteContent: State<EditNoteState> = _noteContent

    private var _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    init {
        val idState = savedStateHandle.getStateFlow("noteId", -1)

        viewModelScope.launch {
            idState.collectLatest { noteId ->
                if (noteId != -1 ) {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        currentId = note.id
                        _noteColor.value = note.color
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )


                    }

                }
            }
        }

      /*  savedStateHandle.get<Int>("noteId")?.let { noteId ->
            Log.v("noteId is ", "$noteId")
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        Log.v("testLog", note.toString())
                        _noteColor.value = note.color
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )


                    }
                }
            }

        }*/


    }

    fun onEvent(addEditNoteEvent: AddEditNoteEvent) {
        when (addEditNoteEvent) {
            is AddEditNoteEvent.TitleChange -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = addEditNoteEvent.text
                )


            }

            is AddEditNoteEvent.ContentChange -> {
                _noteContent.value = noteContent.value.copy(
                    text = addEditNoteEvent.text
                )

            }

            is AddEditNoteEvent.TitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused && noteTitle.value.text.isBlank()
                )

            }

            is AddEditNoteEvent.ContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ColorChange -> {
                _noteColor.value = addEditNoteEvent.color
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentId
                            )
                        )
                        _noteTitle.value = noteTitle.value.copy(
                            text = ""
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = ""
                        )
                        _noteColor.value = Note.noteColors.random().toArgb()

                        _uiEvent.emit(UiEvent.SaveNote)
                    } catch (exception: InvalidNoteException) {
                        _uiEvent.emit(
                            UiEvent.ShowSnackbar(
                                exception.message ?: "Couldn't save note."
                            )
                        )

                    }
                }
            }
        }
    }

    fun setCurrentNoteId(id: Int) {
        savedStateHandle["noteId"] = id
    }
}