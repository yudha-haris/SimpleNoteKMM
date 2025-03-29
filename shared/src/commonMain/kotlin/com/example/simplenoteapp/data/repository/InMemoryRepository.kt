package com.example.simplenoteapp.data.repository

import com.example.simplenoteapp.domain.model.Note
import com.example.simplenoteapp.domain.repository.NoteRepository

class InMemoryRepository : NoteRepository {
    private val notes = mutableListOf<Note>()

    override fun getNotes(): List<Note> = notes.toList()

    override fun getNoteById(id: String): Note? = notes.find { it.id == id }

    override fun addNote(note: Note) {
        notes.add(note)
    }

    override fun deleteNote(id: String) {
        notes.removeAll { it.id == id }
    }
}