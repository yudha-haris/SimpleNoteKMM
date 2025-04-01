package di

import com.example.simplenoteapp.data.repository.InMemoryRepository
import com.example.simplenoteapp.domain.repository.NoteRepository
import com.example.simplenoteapp.domain.useCase.AddNoteUseCase
import com.example.simplenoteapp.domain.useCase.DeleteNoteUseCase
import com.example.simplenoteapp.domain.useCase.GetNoteByIdUseCase
import com.example.simplenoteapp.domain.useCase.GetNotesUseCase
import com.example.simplenoteapp.domain.useCase.NoteUseCases
import org.koin.dsl.module

val appModule = module {
    single<NoteRepository> { InMemoryRepository() }
    factory { GetNotesUseCase(get()) }
    factory { GetNoteByIdUseCase(get()) }
    factory { AddNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory { NoteUseCases(get(), get(), get(), get()) }
}