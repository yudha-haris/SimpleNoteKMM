package com.example.simplenoteapp.features.note.di
import com.example.simplenoteapp.features.note.presentation.viewmodels.NoteDetailViewModel
import com.example.simplenoteapp.features.note.presentation.viewmodels.NoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteModule = module {
    viewModel<NoteDetailViewModel> { NoteDetailViewModel(get()) }
    viewModel<NoteListViewModel> { NoteListViewModel(get()) }
}