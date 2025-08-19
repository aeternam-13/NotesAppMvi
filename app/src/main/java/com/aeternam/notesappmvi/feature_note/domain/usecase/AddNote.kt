
package com.aeternam.notesappmvi.feature_note.domain.usecase


import com.aeternam.notesappmvi.feature_note.domain.model.InvalidNoteException
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository

class AddNote(private val repository : NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note : Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Title is empty")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("Content is empty")
        }
        repository.insertNode(note)
    }
}
