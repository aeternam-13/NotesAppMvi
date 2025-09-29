package com.aeternam.notesappmvi.feature_note.presentation.notes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeternam.notesappmvi.core.Result
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.usecase.NoteUseCases
import com.aeternam.notesappmvi.feature_note.domain.util.NoteOrder
import com.aeternam.notesappmvi.feature_note.domain.util.OrderType
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenState.*
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.NotesScreenUiEvent.*
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen.NotesScreenSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    val useCases: NoteUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<NotesScreenState>(Loading)
    val state: StateFlow<NotesScreenState> = _state

    private val _uiEvent = MutableSharedFlow<NotesScreenUiEvent>()
    val uiEvent: SharedFlow<NotesScreenUiEvent> = _uiEvent

    private var _stateHolder = NotesScreenStateHolder()

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onIntent(intent: NotesScreenIntent) {
        when (intent) {
            is NotesScreenIntent.Order -> {
                if (_stateHolder.noteOrder::class == intent.noteOrder::class && _stateHolder.noteOrder.orderType == intent.noteOrder.orderType) {
                    return
                }
                getNotes(intent.noteOrder)
            }

            is NotesScreenIntent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(intent.note)
                    recentlyDeletedNote = intent.note
                    _uiEvent.emit(
                        ShowSnackBarWithUndo(
                            "Deleted Note", { onIntent(NotesScreenIntent.RestoreNote) })
                    )
                }

            }

            is NotesScreenIntent.RestoreNote -> {
                viewModelScope.launch {
                    if (recentlyDeletedNote == null) return@launch
                    useCases.addNote(recentlyDeletedNote!!)
                    recentlyDeletedNote = null
                }
            }

            is NotesScreenIntent.ToggleOrderSection -> {
                _stateHolder = _stateHolder.copy(
                    isOrderSectionVisible = !_stateHolder.isOrderSectionVisible
                )
                _state.value = Success(_stateHolder)
            }

            is NotesScreenIntent.ToggleAppMode -> {
                _stateHolder =  _stateHolder.copy(appMode = useCases.toggleAppMode())
                _state.value = Success(_stateHolder)
                getNotes(_stateHolder.noteOrder)
            }

            is NotesScreenIntent.Retry -> getNotes(_stateHolder.noteOrder)
        }
    }


    private fun getNotes(noteOrder: NoteOrder) {
        _state.value = Loading
        getNotesJob?.cancel()
        getNotesJob = useCases.getNotes(noteOrder).onEach { result ->
            when (result) {
                is Result.Failure -> _state.value = Error(result.error)
                is Result.Success -> {
                    _stateHolder = _stateHolder.copy(
                        notes = result.data, noteOrder = noteOrder
                    )
                    _state.value = Success(
                        _stateHolder
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}