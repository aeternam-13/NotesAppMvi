package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen

sealed class AddEditNoteState {
    data object Loading : AddEditNoteState()
    data class Editing(val stateHolder: AddEditNoteStateHolder) : AddEditNoteState()
}