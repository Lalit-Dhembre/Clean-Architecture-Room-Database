package com.cosmicStruck.cleanarchitectureroomdb.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.InvalidNoteResponse
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent : State<String> = _noteContent

    private val _noteColor = mutableIntStateOf(Note.noteColor.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId : Int? = null

    init{
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId!= -1){
                Log.d("ID FETCH","${noteId}")
                viewModelScope.launch {
                    notesUseCases.getNote(noteId)?.also { note->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                        _noteColor.intValue = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNotesEvent){
        when(event){
            is AddEditNotesEvent.SaveNote ->{
                viewModelScope.launch {
                    try{
                        notesUseCases.addNote(
                            Note(
                                title = noteTitle.value,
                                content = noteContent.value,
                                color = noteColor.value,
                                timeStamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteResponse){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
            is AddEditNotesEvent.ChangeColor ->{
                _noteColor.value = event.color
            }
            is AddEditNotesEvent.EnteredTitle ->{
                _noteTitle.value = event.value

            }
            is AddEditNotesEvent.EnteredContent ->{
                _noteContent.value = event.value
            }

        }
    }
    sealed class UiEvent{
        data class ShowSnackBar(val message:String) : UiEvent()
        object SaveNote: UiEvent()
    }
}