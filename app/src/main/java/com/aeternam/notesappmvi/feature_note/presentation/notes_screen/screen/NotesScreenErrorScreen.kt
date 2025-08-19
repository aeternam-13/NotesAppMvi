package com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException


@Composable
fun NotesScreenError(noteException: NoteException) {

        Column{
            Text(text = noteException.message)
            Icon(imageVector = Icons.Default.Warning, contentDescription = "Icon Error")
        }

}