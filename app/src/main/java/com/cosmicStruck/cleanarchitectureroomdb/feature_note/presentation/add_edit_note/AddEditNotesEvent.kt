package com.cosmicStruck.cleanarchitectureroomdb.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNotesEvent {
    data class EnteredTitle(val value: String): AddEditNotesEvent()
    data class EnteredContent(val value: String): AddEditNotesEvent()
    data class ChangeColor(val color: Int): AddEditNotesEvent()
    object SaveNote: AddEditNotesEvent()
}
