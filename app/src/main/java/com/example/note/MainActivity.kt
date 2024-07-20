package com.example.note

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note.feature_note.presentation.add_edit_screen.components.AddEditNoteEvent
import com.example.note.feature_note.presentation.add_edit_screen.components.AddEditNoteScreen
import com.example.note.feature_note.presentation.add_edit_screen.components.AddEditNoteViewModel
import com.example.note.feature_note.presentation.note_screen.NoteEvents
import com.example.note.feature_note.presentation.note_screen.NoteScreen
import com.example.note.feature_note.presentation.note_screen.NoteScreenViewModel
import com.example.note.feature_note.presentation.util.Screen
import com.example.note.ui.theme.NoteTheme
import com.example.note.ui.theme.surfaceColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val noteScreenViewModel by viewModels<NoteScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(Screen.NoteScreen.route) {
                            //    val noteScreenViewModel = viewModels<NoteScreenViewModel> ()
                            NoteScreen(
                                navController = navController,
                                state = noteScreenViewModel.state.value,
                                onClickSortIcon = {
                                    noteScreenViewModel.onEvent(NoteEvents.ToggleOrderSection)
                                },
                                onOrderChanges = {
                                    noteScreenViewModel.onEvent(NoteEvents.Order(it))
                                },
                                onDeleteClick = {
                                    noteScreenViewModel.onEvent(NoteEvents.DeleteNote(it))
                                },
                                onRestoreNote = {
                                    noteScreenViewModel.onEvent(NoteEvents.RestoreNote)
                                }
                            )
                        }
                        composable(
                            Screen.AddEditScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val addEditNoteViewModel by viewModels<AddEditNoteViewModel>()

                            val noteColor = it.arguments?.getInt("noteColor") ?: -1
                            val noteId = it.arguments?.getInt("noteId") ?: -1
                            addEditNoteViewModel.setCurrentNoteId(noteId)
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = noteColor,
                                colorInViewModel = addEditNoteViewModel.noteColor.value,
                                titleState = addEditNoteViewModel.noteTitle.value,
                                contentState = addEditNoteViewModel.noteContent.value,
                                uiEvent = addEditNoteViewModel.uiEvent,
                                saveNoteEvent = {
                                    addEditNoteViewModel.onEvent(AddEditNoteEvent.SaveNote)
                                },
                                onChangeColor = {
                                    addEditNoteViewModel.onEvent(
                                        AddEditNoteEvent.ColorChange(
                                            it
                                        )
                                    )
                                },
                                onTitleValueChange = {
                                    addEditNoteViewModel.onEvent(
                                        AddEditNoteEvent.TitleChange(
                                            it
                                        )
                                    )
                                },

                                onTitleFocusChange = {
                                    addEditNoteViewModel.onEvent(
                                        AddEditNoteEvent.TitleFocus(
                                            it
                                        )
                                    )
                                },
                                onContentValueChange = {
                                    addEditNoteViewModel.onEvent(
                                        AddEditNoteEvent.ContentChange(
                                            it
                                        )
                                    )
                                },
                                onContentFocusChange = {
                                    addEditNoteViewModel.onEvent(
                                        AddEditNoteEvent.ContentFocus(
                                            it
                                        )
                                    )
                                }
                            )

                        }

                    }

                }
            }
        }
    }
}

