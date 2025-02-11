package com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase

import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.repository.NoteRepository
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.util.NoteOrder
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)) : Flow<List<Note>>{
        return repository.getNotes().map { note->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> note.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> note.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> note.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> note.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> note.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> note.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}