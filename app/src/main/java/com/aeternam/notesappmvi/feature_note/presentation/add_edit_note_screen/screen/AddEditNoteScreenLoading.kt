package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun AddEditNoteScreenLoading() {

    Column {
        Text(
            text = "Loading note",
            style = MaterialTheme.typography.bodyLarge
        )
        CircularProgressIndicator()
    }
}