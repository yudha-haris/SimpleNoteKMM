package com.example.simplenoteapp.features.note.domain.useCase

import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.repository.NoteRepository

class GetNotesUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(): List<Note> = repository.getNotes()
}