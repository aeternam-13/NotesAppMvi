package com.aeternam.notesappmvi.feature_note.presentation.notes_screen

import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.util.NoteOrder
import com.aeternam.notesappmvi.feature_note.domain.util.OrderType

data class NotesScreenStateHolder(
    val notes : List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false
)
