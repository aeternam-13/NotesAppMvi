package com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aeternam.notesappmvi.feature_note.presentation.destinations.AddEditNoteDestination
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenIntent
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenState
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenUiEvent
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest {
            when (it) {

                is NotesScreenUiEvent.NavigateToAddEditNote -> {
                    navController.navigate(AddEditNoteDestination())
                }

                is NotesScreenUiEvent.ShowSnackBarWithUndo -> {
                    val res = snackHostState.showSnackbar(
                        it.message, actionLabel = "Undo"
                    )

                    if (res == SnackbarResult.ActionPerformed) {
                       it.undoCallback()
                    }
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackHostState) }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(AddEditNoteDestination())
            }

        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
        }
    }, content = { innerPadding ->
        when (val current = state) {
            is NotesScreenState.Error -> NotesScreenError(current.error, {
                viewModel.onIntent(
                    NotesScreenIntent.Retry
                )
            })
            is NotesScreenState.Loading -> NotesScreenLoading()
            is NotesScreenState.Success -> NotesScreenSuccess(
                stateHolder = current.stateHolder,
                modifier = Modifier.padding(innerPadding),
                toggleAppMode = {viewModel.onIntent(NotesScreenIntent.ToggleAppMode)},
                toggleOrderSection = { viewModel.onIntent(NotesScreenIntent.ToggleOrderSection) },
                onOrderChange = { order -> viewModel.onIntent(NotesScreenIntent.Order(order)) },
                onDeleteNote = { note -> viewModel.onIntent(NotesScreenIntent.DeleteNote(note)) },
                navigateToAddEditNote = { note ->
                    navController.navigate(
                        AddEditNoteDestination(
                            note.id ?: -1, note.color
                        )
                    )
                }
            )
        }

    })
}

