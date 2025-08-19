package com.aeternam.notesappmvi.feature_note.presentation.notes_screen

import com.aeternam.notesappmvi.feature_note.domain.model.NoteException

sealed class NotesScreenState {
    data object Loading : NotesScreenState()

    data class Success(val stateHolder: NotesScreenStateHolder) : NotesScreenState()

    data class Error(val error: NoteException) : NotesScreenState()
}


