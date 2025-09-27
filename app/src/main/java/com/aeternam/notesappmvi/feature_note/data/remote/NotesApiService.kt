package com.aeternam.notesappmvi.feature_note.data.remote

import com.aeternam.notesappmvi.feature_note.domain.model.Note
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApiService {
    @GET("notes")
    suspend fun getNotes(): List<Note>

    @POST("notes")
    suspend fun saveNote(@Body note: Note): Note

    @PUT("notes/{id}")
    suspend fun updateNote(@Body note: Note): Note

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") noteId: String)
}