package com.aeternam.notesappmvi.feature_note.domain.repository

import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.domain.model.AppMode
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<Result<List<Note>, NoteException>>

    suspend fun getNoteByID(id:Int): Note?

    suspend fun insertNode(note : Note)

    suspend fun deleteNote(note: Note)

    fun toggleAppMode() : AppMode

}