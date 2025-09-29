package com.aeternam.notesappmvi.feature_note.data.remote

import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.data.NoteDao
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteApiException

import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import com.aeternam.notesappmvi.feature_note.domain.model.NoteIOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import retrofit2.HttpException
import java.io.IOException


class NoteDaoApi(
    val apiService : NotesApiService
) : NoteDao{

    private var _notes = mutableListOf<Note>()

    override fun getNotes(): Flow<Result<List<Note>, NoteException>> {
        return flow {
            runCatching {
                val notes = apiService.getNotes()
                _notes = notes.toMutableList()
                emit(Result.Success(notes))
            }.onFailure { e ->
                when (e) {
                    is IOException -> {
                        emit(Result.Failure(NoteIOException(e)))
                    }

                    is HttpException -> {
                        val errorCode = e.code()
                        val errorResponse = e.response()?.errorBody()?.string() ?: ""
                        emit(Result.Failure(NoteApiException(errorResponse, errorCode)))
                    }

                    else -> {
                        emit(Result.Failure(NoteException(e.message ?: "")))
                    }
                }
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return _notes.find { note -> note.id == id }
    }

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(note: Note): Result<Unit, NoteException> {
        TODO("Not yet implemented")
    }

}