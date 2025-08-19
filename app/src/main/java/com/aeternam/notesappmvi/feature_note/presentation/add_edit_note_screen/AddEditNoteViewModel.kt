package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen

import androidx.lifecycle.ViewModel
import com.aeternam.notesappmvi.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    useCases: NoteUseCases
) : ViewModel(){

}