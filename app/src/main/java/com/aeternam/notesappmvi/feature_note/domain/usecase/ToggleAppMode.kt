package com.aeternam.notesappmvi.feature_note.domain.usecase

import com.aeternam.notesappmvi.feature_note.domain.model.AppMode
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository

class ToggleAppMode(val repository: NoteRepository) {
    operator fun invoke(): AppMode {
        return repository.toggleAppMode()
    }
}