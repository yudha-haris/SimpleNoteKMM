package com.example.simplenoteapp.presentation.note.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenoteapp.core.utils.Result
import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.useCase.NoteUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.UUID

class NoteViewModel(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _notes = MutableStateFlow<Result<List<Note>>>(Result.Loading)
    val notes: StateFlow<Result<List<Note>>> get() = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            noteUseCases.getNotes()
                .catch { it.printStackTrace() }
                .collect { _notes.value = it }
        }
    }

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title,
            content
        )
        viewModelScope.launch {
            noteUseCases.addNote(newNote)
                .catch { it.printStackTrace() }
                .collect { loadNotes() }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            noteUseCases.deleteNote(id)
                .catch { it.printStackTrace() }
                .collect { loadNotes() }
        }
    }
}