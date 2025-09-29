package com.aeternam.notesappmvi.feature_note.domain.usecase

import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository

class UpdateNote(val repository: NoteRepository) {

    suspend operator fun invoke(note : Note) : Result<Unit, NoteException> {
        return repository.updateNote(note)
    }
}