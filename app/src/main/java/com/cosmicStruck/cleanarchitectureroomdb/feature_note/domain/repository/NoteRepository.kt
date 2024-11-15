package com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.repository

import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


// MAKING IT AS A INTERFACE IS BENEFICIAL FOR TESTING BY CREATING ITS FAKE VERSION SO IT DOES NOT PERFORM OPERATION ON REAL DATABASE
interface NoteRepository {

    fun getNotes() : Flow<List<Note>>

    suspend fun getNoteById(id : Int?) : Note?

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun addNote(note: Note)
}