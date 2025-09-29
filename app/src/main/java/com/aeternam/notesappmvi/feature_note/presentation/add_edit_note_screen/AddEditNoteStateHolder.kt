package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen

data class AddEditNoteStateHolder(
    val title: NoteTextFieldState = NoteTextFieldState(hint = "Enter title"),
    val content: NoteTextFieldState = NoteTextFieldState(hint = "Enter some content"),
    val color: Long = -1
)

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)