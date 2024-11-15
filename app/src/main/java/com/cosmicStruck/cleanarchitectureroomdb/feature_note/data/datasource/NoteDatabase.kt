package com.cosmicStruck.cleanarchitectureroomdb.feature_note.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note

@Database(version = 2, entities = [Note::class])
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao : NoteDao

    companion object{
        const val databaseName = "NotesDB"
    }
}