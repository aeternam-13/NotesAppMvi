package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeternam.notesappmvi.feature_note.domain.model.InvalidNoteException
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    val useCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _stateHolder = AddEditNoteStateHolder(color = Note.noteColors.random().toArgb().toLong())
    private val _state = MutableStateFlow<AddEditNoteState>(AddEditNoteState.Editing(_stateHolder))
    val state: StateFlow<AddEditNoteState> = _state
    private var currentNoteId: Int? = null
    private val _uiEvent = MutableSharedFlow<AddEditNoteUiEvent>()
    val uiEvent: SharedFlow<AddEditNoteUiEvent> = _uiEvent

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId == -1) return@let

            viewModelScope.launch {
                useCases.getNote(noteId)?.also { note ->
                    currentNoteId = note.id
                    _stateHolder = _stateHolder.copy(
                        title = NoteTextFieldState(text = note.title, isHintVisible = false),
                        content = NoteTextFieldState(
                            text = note.content,
                            isHintVisible = false
                        ),
                        color = note.color
                    )
                }
            }
        }
    }

    fun onIntent(intent: AddEditNoteIntent) {
        when (intent) {
            is AddEditNoteIntent.ChangeColor -> {
                _stateHolder = _stateHolder.copy(color = intent.color)
                _state.value = AddEditNoteState.Editing(_stateHolder)
            }

            is AddEditNoteIntent.ChangeContentFocus -> {
                _stateHolder =
                    _stateHolder.copy(
                        content = _stateHolder.content.copy(
                            isHintVisible = !intent.focusState.isFocused && _stateHolder.content.text.isBlank()
                        )
                    )
                _state.value = AddEditNoteState.Editing(_stateHolder)
            }

            is AddEditNoteIntent.ChangeTitleFocus -> {
                _stateHolder =
                    _stateHolder.copy(
                        title = _stateHolder.title.copy(
                            isHintVisible = !intent.focusState.isFocused && _stateHolder.title.text.isBlank()
                        )
                    )
                _state.value = AddEditNoteState.Editing(_stateHolder)
            }

            is AddEditNoteIntent.EnteredContent -> {
                _stateHolder =
                    _stateHolder.copy(
                        content = _stateHolder.content.copy(
                            text = intent.value
                        )
                    )
                _state.value = AddEditNoteState.Editing(_stateHolder)
            }

            is AddEditNoteIntent.EnteredTitle -> {
                _stateHolder =
                    _stateHolder.copy(
                        title = _stateHolder.title.copy(
                            text = intent.value
                        )
                    )
                _state.value = AddEditNoteState.Editing(_stateHolder)
            }

            AddEditNoteIntent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        useCases.addNote(
                            Note(
                                title = _stateHolder.title.text,
                                content = _stateHolder.content.text,
                                color = _stateHolder.color,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _uiEvent.emit(AddEditNoteUiEvent.NavigateBack)
                    } catch (e: InvalidNoteException) {
                        _uiEvent.emit(AddEditNoteUiEvent.ShowSnackBar(message = "Couldn't save note"))
                    }
                }
            }
        }
    }


}


fun assda(){
    val exampleList = mutableListOf<Char>()
    exampleList.last()
}