package com.aeternam.notesappmvi.feature_note.domain.usecase

import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository


class DeleteNote (private val repository: NoteRepository){
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}