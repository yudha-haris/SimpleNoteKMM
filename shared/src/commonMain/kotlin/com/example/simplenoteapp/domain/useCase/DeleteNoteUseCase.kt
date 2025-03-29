package com.example.simplenoteapp.domain.useCase

import com.example.simplenoteapp.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    operator fun invoke(id: String) = repository.deleteNote(id)
}