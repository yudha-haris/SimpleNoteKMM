package com.example.simplenoteapp.presentation

import androidx.lifecycle.ViewModel
import com.example.simplenoteapp.domain.model.Note
import com.example.simplenoteapp.domain.useCase.NoteUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class NoteViewModel(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        _notes.value = noteUseCases.getNotes();
    }

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title,
            content
        )
        noteUseCases.addNote(newNote)
        loadNotes()
    }

    fun deleteNote(id: String) {
        noteUseCases.deleteNote(id)
        loadNotes()
    }
}