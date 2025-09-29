package com.aeternam.notesappmvi.feature_note.domain.model

import java.io.IOException

open class NoteException(override val message: String) : Exception()

class NoteApiException(message: String, val statusCode: Int) : NoteException(message)

class NoteIOException(exception : IOException) : NoteException(message = exception.message ?: "")

class NoteStreamException(message: String) : NoteException(message)

class NoteDataBaseException(message: String) : NoteException(message)

class InvalidNoteException(message: String) : NoteException(message)
