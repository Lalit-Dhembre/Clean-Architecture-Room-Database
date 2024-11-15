package com.cosmicStruck.cleanarchitectureroomdb.feature_note.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notesTable")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM notesTable WHERE id = :id")
    suspend fun getNote(id:Int?) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}