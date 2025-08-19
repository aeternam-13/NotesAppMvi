package com.aeternam.notesappmvi.feature_note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aeternam.notesappmvi.feature_note.data.NoteDao
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import kotlinx.coroutines.flow.Flow
import com.aeternam.notesappmvi.core.Result

@Dao
interface NoteDaoLocal : NoteDao {
    @Query("SELECT * FROM note")
    override fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    override suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict =  OnConflictStrategy.Companion.REPLACE)
    override suspend fun insertNote(note : Note)

    @Delete
    override suspend fun deleteNote(note: Note)
}

class ExtensibleClass{

}

fun ExtensibleClass.extendedBehavior() : String{
    return ""
}


fun returnable(retVal : String?) : String{
    retVal?.let {
        return it + "asddsa"
    }
    return ""
}


fun sum(a : Int , b : Int) : Int {
    return (a + b).also { it + 1 }
}

