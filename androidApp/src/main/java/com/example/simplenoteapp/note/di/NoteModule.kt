package com.example.simplenoteapp.note.di
import com.example.simplenoteapp.note.presentation.viewmodels.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteModule = module {
    viewModel<NoteViewModel> { NoteViewModel(get()) }
}