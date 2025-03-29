package com.example.simplenoteapp.domain.useCase

import com.example.simplenoteapp.domain.repository.NoteRepository

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNoteById: GetNoteByIdUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
) {
    companion object {
        fun create(repository: NoteRepository) = NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            getNoteById = GetNoteByIdUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository)
        )
    }
}
