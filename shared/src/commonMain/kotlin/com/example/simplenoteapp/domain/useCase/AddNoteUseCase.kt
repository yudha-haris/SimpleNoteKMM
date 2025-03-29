package com.example.simplenoteapp.domain.useCase

import com.example.simplenoteapp.domain.model.Note
import com.example.simplenoteapp.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    operator fun invoke(note: Note) = repository.addNote(note)
}