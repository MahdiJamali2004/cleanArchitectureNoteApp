package com.example.note.di

import android.app.Application
import androidx.room.Room
import com.example.note.feature_note.data.data_source.NoteDatabase
import com.example.note.feature_note.data.repository.NoteRepositoryImpl
import com.example.note.feature_note.domain.repository.NoteRepository
import com.example.note.feature_note.domain.use_cases.DeleteNoteUseCase
import com.example.note.feature_note.domain.use_cases.GetNoteUseCase
import com.example.note.feature_note.domain.use_cases.GetNotesUseCase
import com.example.note.feature_note.domain.use_cases.InsertNoteUseCase
import com.example.note.feature_note.domain.use_cases.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }

    @Provides
    @Singleton
    fun providesNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            DeleteNoteUseCase(noteRepository),
            GetNotesUseCase(noteRepository),
            GetNoteUseCase(noteRepository),
            InsertNoteUseCase(noteRepository)
        )
    }
}