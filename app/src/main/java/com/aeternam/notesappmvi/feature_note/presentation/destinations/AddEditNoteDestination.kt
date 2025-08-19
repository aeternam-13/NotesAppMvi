package com.aeternam.notesappmvi.feature_note.presentation.destinations

import kotlinx.serialization.Serializable

@Serializable
data class AddEditNoteDestination(val noteId: Int = -1, val noteColor: Int = -1)