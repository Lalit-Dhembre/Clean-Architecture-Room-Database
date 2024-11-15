package com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase

data class NotesUseCases(
    val getNotes: GetNotes,
    val deleteNotes: DeleteNotes,
    val addNote: AddNote,
    val getNote: GetNote
)
