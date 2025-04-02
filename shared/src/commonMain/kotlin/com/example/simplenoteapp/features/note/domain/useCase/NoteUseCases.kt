package com.example.simplenoteapp.features.note.domain.useCase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNoteById: GetNoteByIdUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
)
