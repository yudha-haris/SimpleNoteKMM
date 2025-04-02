package com.example.simplenoteapp.features.note.domain.useCase

import com.example.simplenoteapp.features.note.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: String) = repository.deleteNote(id)
}