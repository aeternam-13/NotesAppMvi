
package com.aeternam.notesappmvi.feature_note.domain.usecase


import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.model.NoteException
import com.aeternam.notesappmvi.feature_note.domain.repository.NoteRepository
import com.aeternam.notesappmvi.feature_note.domain.util.NoteOrder
import com.aeternam.notesappmvi.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<Result<List<Note>, NoteException>> {
        return repository.getNotes().map { result ->
            when (result) {
                is Result.Failure -> result
                is Result.Success -> Result.Success(orderNotes(noteOrder, result.data))
            }
        }
    }

    private fun orderNotes(noteOrder: NoteOrder, notes: List<Note>): List<Note> {
        return when (noteOrder.orderType) {
            is OrderType.Ascending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                    is NoteOrder.Color -> notes.sortedBy { it.color }
                }
            }

            is OrderType.Descending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                    is NoteOrder.Color -> notes.sortedByDescending { it.color }
                }
            }
        }
    }
}