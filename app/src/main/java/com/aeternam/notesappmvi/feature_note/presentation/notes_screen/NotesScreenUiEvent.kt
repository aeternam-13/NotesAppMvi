package com.aeternam.notesappmvi.feature_note.presentation.notes_screen

sealed class NotesScreenUiEvent {

    data class ShowSnackBarWithUndo(
        val message: String, val undoCallback: () -> Unit
    ) : NotesScreenUiEvent()

    data class NavigateToAddEditNote(
        val noteId: Int
    ) : NotesScreenUiEvent()

}