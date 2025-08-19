package com.aeternam.notesappmvi.feature_note.domain.usecase

import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository


class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id : Int) : Note?{
        return repository.getNoteByID(id)
    }
}