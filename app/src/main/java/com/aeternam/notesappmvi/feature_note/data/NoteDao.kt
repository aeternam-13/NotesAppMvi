package com.aeternam.notesappmvi.feature_note.data


import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import kotlinx.coroutines.flow.Flow
import com.aeternam.notesappmvi.core.Result

interface NoteDao {

    fun getNotes() : Flow<Result<List<Note>, NoteException>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note : Note)

    suspend fun deleteNote(note: Note)
}