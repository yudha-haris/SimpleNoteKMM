package com.example.simplenoteapp.domain.useCase

import com.example.simplenoteapp.domain.model.Note
import com.example.simplenoteapp.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val repository: NoteRepository) {
    operator fun invoke(id: String): Note? = repository.getNoteById(id)
}