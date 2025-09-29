package com.aeternam.notesappmvi.di

import android.app.Application
import androidx.room.Room
import com.aeternam.notesappmvi.feature_note.data.local.NoteDaoLocal
import com.aeternam.notesappmvi.feature_note.data.local.NoteDatabase
import com.aeternam.notesappmvi.feature_note.data.remote.NoteDaoApi
import com.aeternam.notesappmvi.feature_note.data.remote.NotesApiService
import com.aeternam.notesappmvi.feature_note.data.repository.NoteRepositoryImpl
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository
import com.aeternam.notesappmvi.feature_note.domain.usecase.AddNote
import com.aeternam.notesappmvi.feature_note.domain.usecase.DeleteNote
import com.aeternam.notesappmvi.feature_note.domain.usecase.GetNote
import com.aeternam.notesappmvi.feature_note.domain.usecase.GetNotes
import com.aeternam.notesappmvi.feature_note.domain.usecase.NoteUseCases
import com.aeternam.notesappmvi.feature_note.domain.usecase.ToggleAppMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(app,NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideNoteDaoDatabase(db: NoteDatabase): NoteDaoLocal {
        return NoteDaoLocal(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMyApiService(retrofit: Retrofit): NotesApiService {
        return retrofit.create(NotesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteDaoApi(apiService : NotesApiService) : NoteDaoApi {
        return NoteDaoApi(apiService)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(daoLocal: NoteDaoLocal, daoApi: NoteDaoApi): NoteRepository {
        return NoteRepositoryImpl(daoLocal, daoApi)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) : NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
            toggleAppMode = ToggleAppMode(repository)
        )
    }


}