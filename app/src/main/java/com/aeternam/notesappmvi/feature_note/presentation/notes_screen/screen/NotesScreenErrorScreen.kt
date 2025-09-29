package com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException


@Composable
fun NotesScreenError(noteException: NoteException, retry: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            SelectionContainer {
                Text(text = noteException.message)
            }
            Icon(imageVector = Icons.Default.Warning, contentDescription = "Icon Error")
            Button(onClick = retry) { Text("Retry") }
        }
    }

}