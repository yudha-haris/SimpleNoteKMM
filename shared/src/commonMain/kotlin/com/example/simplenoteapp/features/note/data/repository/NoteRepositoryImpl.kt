package com.example.simplenoteapp.features.note.data.repository

import com.example.simplenoteapp.features.note.data.remote.NoteApiService
import com.example.simplenoteapp.features.note.data.remote.mapper.toDTO
import com.example.simplenoteapp.features.note.data.remote.mapper.toDomain
import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.repository.NoteRepository

class NoteRepositoryImpl(private val apiService: NoteApiService) : NoteRepository {
    override suspend fun getNotes(): List<Note> {
        return apiService.getNotes().map { it.toDomain() }
    }

    override suspend fun getNoteById(id: String): Note? {
        return apiService.getNoteById(id).toDomain()
    }

    override suspend fun addNote(note: Note) {
        return apiService.addNote(note = note.toDTO())
    }

    override suspend fun deleteNote(id: String) {
        return apiService.deleteNote(id)
    }
}