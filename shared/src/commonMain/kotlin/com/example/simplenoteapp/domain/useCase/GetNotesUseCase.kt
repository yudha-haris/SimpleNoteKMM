package com.example.simplenoteapp.domain.useCase

import com.example.simplenoteapp.domain.model.Note
import com.example.simplenoteapp.domain.repository.NoteRepository

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): List<Note> = repository.getNotes()
}