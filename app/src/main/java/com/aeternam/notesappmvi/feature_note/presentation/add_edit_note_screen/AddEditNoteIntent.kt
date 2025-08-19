package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteIntent {
    data class EnteredTitle(val value : String) : AddEditNoteIntent()
    data class ChangeTitleFocus(val focusState : FocusState) : AddEditNoteIntent()
    data class EnteredContent(val value : String) : AddEditNoteIntent()
    data class ChangeContentFocus(val focusState : FocusState) : AddEditNoteIntent()
    data class ChangeColor(val color : Int) : AddEditNoteIntent()
    data object SaveNote : AddEditNoteIntent()
}