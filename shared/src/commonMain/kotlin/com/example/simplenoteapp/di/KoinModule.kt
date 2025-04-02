package com.example.simplenoteapp.di

import com.example.simplenoteapp.core.remote.createHttpClient
import com.example.simplenoteapp.features.note.data.remote.NoteApiService
import com.example.simplenoteapp.features.note.data.repository.NoteRepositoryImpl
import com.example.simplenoteapp.features.note.domain.repository.NoteRepository
import com.example.simplenoteapp.features.note.domain.useCase.AddNoteUseCase
import com.example.simplenoteapp.features.note.domain.useCase.DeleteNoteUseCase
import com.example.simplenoteapp.features.note.domain.useCase.GetNoteByIdUseCase
import com.example.simplenoteapp.features.note.domain.useCase.GetNotesUseCase
import com.example.simplenoteapp.features.note.domain.useCase.NoteUseCases
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { NoteApiService(get()) }
    single<NoteRepository> { NoteRepositoryImpl(get()) }

    factory { GetNotesUseCase(get()) }
    factory { GetNoteByIdUseCase(get()) }
    factory { AddNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory { NoteUseCases(get(), get(), get(), get()) }
}