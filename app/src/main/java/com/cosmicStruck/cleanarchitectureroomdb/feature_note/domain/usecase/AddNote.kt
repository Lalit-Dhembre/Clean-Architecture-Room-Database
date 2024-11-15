package com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase

import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.InvalidNoteResponse
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteResponse::class)
    suspend operator fun invoke(note: Note){

        if (note.title.isBlank()){
            throw InvalidNoteResponse("Note's Title can't be Blank")
        }
        if(note.content.isBlank()){
            throw InvalidNoteResponse("Note's Content can't be Blank")
        }
        repository.addNote(note)
    }
}