package com.aeternam.notesappmvi.feature_note.presentation.notes_screen

import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.util.NoteOrder

sealed class NotesScreenIntent {
    data class Order(val noteOrder : NoteOrder) : NotesScreenIntent()
    data class DeleteNote(val note : Note) : NotesScreenIntent()
    data object RestoreNote : NotesScreenIntent()
    data object ToggleOrderSection : NotesScreenIntent()

}