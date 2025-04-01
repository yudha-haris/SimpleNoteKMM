package com.example.simplenoteapp.presentation.note.di
import com.example.simplenoteapp.presentation.note.viewmodels.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteModule = module {
    viewModel<NoteViewModel> {NoteViewModel(get())}
}