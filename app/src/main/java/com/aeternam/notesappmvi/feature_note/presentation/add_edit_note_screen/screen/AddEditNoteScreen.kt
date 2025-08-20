package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.AddEditNoteViewModel

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteId : Int,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
        val state by viewModel.state.collectAsStateWithLifecycle()



//    val noteBackgroundAnimation = remember {
//        Animatable(
//            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
//        )
//    }
}