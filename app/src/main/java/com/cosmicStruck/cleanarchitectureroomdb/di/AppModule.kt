package com.cosmicStruck.cleanarchitectureroomdb.di

import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.data.datasource.NoteDatabase
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.data.repository.NoteRepositoryImpl
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.repository.NoteRepository
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.AddNote
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.DeleteNotes
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.GetNote
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.GetNotes
import com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.usecase.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context) : NoteDatabase{
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.databaseName
        ).fallbackToDestructiveMigrationFrom(startVersions = intArrayOf(1,2))
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: NoteRepository): NotesUseCases{
        return NotesUseCases(
            getNotes = GetNotes(repository),
            deleteNotes = DeleteNotes(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}