package com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase

import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.repository.NoteRepository

class DeleteNotes(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}