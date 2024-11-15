package com.cosmicStruck.cleanarchitectureroomdb.feature_note.data.repository

import com.cosmicStruck.cleanarchitectureroomdb.feature_note.data.datasource.NoteDao
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository{
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int?): Note? {
        return dao.getNote(id)
    }

    override suspend fun updateNote(note: Note) {
        return dao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

    override suspend fun addNote(note: Note) {
        return dao.addNote(note)
    }

}