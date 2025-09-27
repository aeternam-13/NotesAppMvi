package com.aeternam.notesappmvi.feature_note.data.remote

import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.data.NoteDao
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import kotlinx.coroutines.flow.Flow

class NoteDaoApi(
    val apiService : NotesApiService
) : NoteDao{

    private var _notes = mutableListOf<Note>()

    override  fun getNotes(): Flow<Result<List<Note>, NoteException>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: Int): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

}