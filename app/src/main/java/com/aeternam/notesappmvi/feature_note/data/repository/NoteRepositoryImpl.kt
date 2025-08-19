package com.aeternam.notesappmvi.feature_note.data.repository

import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.core.Result.*
import com.aeternam.notesappmvi.feature_note.data.NoteDao
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteDataBaseException
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<Result<List<Note>, NoteException>> {
        return dao.getNotes().map { notes ->
                Success(notes)
            }.catch { exception ->
                Failure(NoteDataBaseException(exception.message ?: "An unknown error occurred"))
            }
    }

    override suspend fun getNoteByID(id: Int): Note? {
        return  dao.getNoteById(id)
    }

    override suspend fun insertNode(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}