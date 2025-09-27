package com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aeternam.notesappmvi.feature_note.domain.model.AppMode
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.util.NoteOrder
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenStateHolder
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen.components.NoteItem
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen.components.OrderSection

@Composable
fun NotesScreenSuccess(
    stateHolder: NotesScreenStateHolder,
    modifier : Modifier,
    toggleOrderSection : () -> Unit,
    toggleAppMode: () -> Unit,
    onOrderChange : (NoteOrder) -> Unit,
    onDeleteNote : (Note) -> Unit,
    navigateToAddEditNote: (Note) -> Unit
){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "NotesApp", style = MaterialTheme.typography.headlineLarge
                )
                IconButton(
                    onClick = toggleAppMode,
                ) {
                    Icon(
                        imageVector = when (stateHolder.appMode) {
                            AppMode.Api -> Icons.Default.Check
                            AppMode.Disk -> Icons.Default.Close
                        },
                        contentDescription = "toggleMode"
                    )
                }
                IconButton(
                    onClick = toggleOrderSection,
                ) {
                    Icon(imageVector = Icons.Default.Build, contentDescription = "Sort")
                }



            }
            AnimatedVisibility(
                visible = stateHolder.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    noteOrder = stateHolder.noteOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onOrderChange = onOrderChange
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items( stateHolder.notes) { note ->
                    NoteItem (
                        note = note, modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp)
                            .clickable {

                                navigateToAddEditNote(note)
//                                navController.navigate(
//                                    Screens.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
//                                )
                            },

                        onDeleteClick ={ onDeleteNote(note)})

                }
            }
        }

}