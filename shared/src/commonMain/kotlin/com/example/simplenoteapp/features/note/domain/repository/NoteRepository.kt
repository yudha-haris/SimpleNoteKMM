package com.example.simplenoteapp.features.note.domain.repository

import com.example.simplenoteapp.features.note.domain.model.Note

interface NoteRepository {
    suspend fun getNotes(): List<Note>
    suspend fun getNoteById(id: String): Note?
    suspend fun addNote(note: Note)
    suspend fun deleteNote(id: String)
}