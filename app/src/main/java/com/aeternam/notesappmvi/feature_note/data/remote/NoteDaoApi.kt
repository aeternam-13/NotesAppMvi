package com.aeternam.notesappmvi.feature_note.data.remote

import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.data.NoteDao
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteApiException

import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import com.aeternam.notesappmvi.feature_note.domain.model.NoteIOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import retrofit2.HttpException
import java.io.IOException


class NoteDaoApi(
    val apiService : NotesApiService
) : NoteDao{
    private var _notes = mutableListOf<Note>()
    private val _flow = MutableSharedFlow<Result<List<Note>, NoteException>>()

    override fun getNotes(): Flow<Result<List<Note>, NoteException>> {
        return _flow.onStart {
            try {
                val notes = apiService.getNotes()
                _notes = notes.toMutableList()
                emit(Result.Success(notes))
            } catch (e: Exception) {
                emit(handleApiCallException(e))
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return _notes.find { note -> note.id == id }
    }

    override suspend fun insertNote(note: Note): Result<Unit, NoteException> {
        try {
            val savedNote = apiService.saveNote(note)
            _notes.add(savedNote)
            _flow.emit(Result.Success(_notes.toList()))
            return Result.Success(Unit)
        } catch (e: Exception) {
            return handleApiCallException(e)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit, NoteException> {
        try {
            note.id?.let {
                apiService.deleteNote(it)
                _notes.remove(note)
                _flow.emit(Result.Success(_notes.toList()))
            }
            return Result.Success(Unit)
        } catch (e: Exception) {
            return handleApiCallException(e)
        }
    }

    override suspend fun updateNote(note: Note): Result<Unit, NoteException> {
        try {
            note.id?.let {
                val updatedNote = apiService.updateNote(it, note)
                val index = _notes.indexOfFirst { it.id == updatedNote.id }
                if (index != -1) {
                    _notes.set(index, updatedNote)
                }
                _flow.emit(Result.Success(_notes.toList()))
            }
            return Result.Success(Unit)
        } catch (
            e: Exception
        ) {
            return handleApiCallException(e)
        }
    }

    private fun handleApiCallException(e: Exception): Result.Failure<NoteException> {
        return when (e) {
            is IOException -> Result.Failure(NoteIOException(e))
            is HttpException -> {
                val errorCode = e.code()
                val errorResponse = e.response()?.errorBody()?.string() ?: ""
                Result.Failure(NoteApiException(errorResponse, errorCode))
            }

            else -> Result.Failure(NoteException(e.message ?: ""))
        }
    }

}