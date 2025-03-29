package com.example.simplenoteapp.domain.repository

import com.example.simplenoteapp.domain.model.Note

interface NoteRepository {
    fun getNotes(): List<Note>
    fun getNoteById(id: String): Note?
    fun addNote(note: Note)
    fun deleteNote(id: String)
}