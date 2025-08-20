package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen

sealed class AddEditNoteUiEvent {

    data object NavigateBack : AddEditNoteUiEvent()

    data class ShowSnackBar(val message: String) : AddEditNoteUiEvent()

    data class ShowLoadingDialog(val message: String) : AddEditNoteUiEvent()

    data object CloseDialog : AddEditNoteUiEvent()
}