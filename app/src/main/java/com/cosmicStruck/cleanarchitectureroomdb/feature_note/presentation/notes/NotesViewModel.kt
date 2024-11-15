package com.cosmicStruck.cleanarchitectureroomdb.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.NotesUseCases
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.util.NoteOrder
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel(){
    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var restoredNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(noteOrder = NoteOrder.Date(orderType = OrderType.Descending))
    }
    fun onEvent(events: NoteEvents){
        when(events){
            is NoteEvents.DeleteNote ->{
                viewModelScope.launch {
                    restoredNote = events.note
                    notesUseCases.deleteNotes(note = events.note)
                }
            }
            is NoteEvents.RestoreNote ->{
                viewModelScope.launch {
                    notesUseCases.addNote(note = restoredNote ?: return@launch)
                    restoredNote = null
                }
            }
            is NoteEvents.Order ->{
                if(state.value.noteOrder::class == events.noteOrder ::class && state.value.noteOrder.orderType == events.noteOrder.orderType){
                    return
                }
                getNotes(noteOrder = events.noteOrder)
            }
            is NoteEvents.ToggleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(noteOrder)
            .onEach {note->
                _state.value = state.value.copy(
                    noteOrder = noteOrder,
                    notes = note
                )
            }.launchIn(viewModelScope)
    }
}