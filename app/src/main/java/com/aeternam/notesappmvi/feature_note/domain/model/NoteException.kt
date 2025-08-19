package com.aeternam.notesappmvi.feature_note.domain.model

sealed class NoteException(override val message: String) : Exception()

class NoteApiException(message: String, val statusCode: Int) : NoteException(message)

class NoteStreamException(message: String) : NoteException(message)

class NoteDataBaseException (message: String) : NoteException(message)

class InvalidNoteException(message: String) : NoteException(message)
