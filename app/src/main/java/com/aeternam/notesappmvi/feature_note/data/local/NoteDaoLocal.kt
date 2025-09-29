package com.aeternam.notesappmvi.feature_note.data.local

import com.aeternam.notesappmvi.feature_note.data.NoteDao
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import kotlinx.coroutines.flow.Flow
import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.core.Result.*
import com.aeternam.notesappmvi.feature_note.domain.model.NoteDataBaseException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class NoteDaoLocal(
    private val dao : NoteDaoRoom
) : NoteDao {

    override  fun getNotes(): Flow<Result<List<Note>, NoteException>> {
        // This is the Flow from your database. It automatically emits on changes.
        val notesFlow = dao.getNotes()

        return flow<Result<List<Note>, NoteException>> {

            val initialNotes = notesFlow.first()
            emit(Success(initialNotes))

            notesFlow.collect { notes ->
                emit(Success(notes))
            }
        }.catch { throwable ->
            emit(
                Failure(
                    NoteDataBaseException(
                        throwable.message ?: "An unknown database error occurred."
                    )
                )
            )
        }
    }

    override suspend fun getNoteById(id: Int): Note?{
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note): Result<Unit, NoteException> {
        try {
            dao.insertNote(note)
            return Success(Unit)
        } catch (e: Exception) {
            return Failure(NoteDataBaseException(e.message ?: ""))
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit, NoteException> {
        try {
            dao.deleteNote(note)
            return Success(Unit)
        } catch (e: Exception) {
            return Failure(NoteDataBaseException(e.message ?: ""))
        }
    }

    override suspend fun updateNote(note: Note): Result<Unit, NoteException> {
        try {
            dao.insertNote(note)
            return Success(Unit)
        } catch (e: Exception) {
            return Failure(NoteDataBaseException(e.message ?: ""))
        }
    }
}

