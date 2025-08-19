package com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun NotesScreenLoading() {
    Column {
        CircularProgressIndicator(
            progress = 0.5f, // 50% progress
            strokeWidth = 4.dp, color = MaterialTheme.colorScheme.primary
        )
        Text("Loading notes ...")
    }
}