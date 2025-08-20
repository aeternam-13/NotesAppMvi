package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.AddEditNoteIntent
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.AddEditNoteState
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.AddEditNoteUiEvent
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.AddEditNoteViewModel
import com.aeternam.notesappmvi.feature_note.presentation.destinations.AddEditNoteDestination
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenUiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteId : Int,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimation = remember {
        Animatable(
            Color(getBackgroundColor(noteColor, state))
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is AddEditNoteUiEvent.CloseDialog -> TODO()
                is AddEditNoteUiEvent.NavigateBack -> navController.navigateUp()
                is AddEditNoteUiEvent.ShowLoadingDialog -> TODO()
                is AddEditNoteUiEvent.ShowSnackBar -> TODO()
            }
        }
    }


    Scaffold(snackbarHost = { SnackbarHost(snackHostState) }, floatingActionButton = {
        if (state is AddEditNoteState.Editing)
            FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { viewModel.onIntent(AddEditNoteIntent.SaveNote) }
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "Save note")
        } else null
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(noteBackgroundAnimation.value)
                .fillMaxSize()
                .padding(innerPadding)


        ) {
            when (val current = state) {
                is AddEditNoteState.Editing -> AddEditNoteScreenEditing(
                    current.stateHolder,
                    { color ->
                        scope.launch {
                            noteBackgroundAnimation.animateTo(
                                targetValue = Color(color),
                                animationSpec = tween(durationMillis = 500)
                            )
                        }
                        viewModel.onIntent(AddEditNoteIntent.ChangeColor(color))
                    },

                    enteredTitle = {
                        viewModel.onIntent(AddEditNoteIntent.EnteredTitle(it))
                    },
                    changeTitleFocus = { viewModel.onIntent(AddEditNoteIntent.ChangeTitleFocus(it)) },
                    enteredContent = {
                        viewModel.onIntent(AddEditNoteIntent.EnteredContent(it))
                    },
                    changeContentFocus = {
                        viewModel.onIntent(
                            AddEditNoteIntent.ChangeContentFocus(
                                it
                            )
                        )
                    }
                )

                is AddEditNoteState.Loading -> AddEditNoteScreenLoading()
            }
        }

    }
}

private fun getBackgroundColor(noteColor: Int, state: AddEditNoteState): Int {
    if (noteColor != -1) return noteColor
    if (state is AddEditNoteState.Editing) return state.stateHolder.color
    return Color.Black.toArgb()
}

