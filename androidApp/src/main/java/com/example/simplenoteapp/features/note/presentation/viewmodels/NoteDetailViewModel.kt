package com.example.simplenoteapp.features.note.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenoteapp.core.utils.UiState
import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.useCase.NoteUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID

class NoteDetailViewModel(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _currentNote = MutableStateFlow<UiState<Note?>>(UiState.Loading)
    val currentNote: StateFlow<UiState<Note?>> get() = _currentNote

    fun getNoteById(id: String) {
        if (id == "new") {
            _currentNote.value = UiState.Success(null)
            return
        }

        viewModelScope.launch {
            flow {
                emit(noteUseCases.getNoteById(id))
            }
                .catch {
                    _currentNote.value =
                        UiState.Error(message = it.message.toString(), code = it.hashCode())
                }
                .collect { _currentNote.value = UiState.Success(it) }
        }
    }

    fun saveNote(id: String, title: String, content: String): Boolean {
        if (title.isBlank() || content.isBlank()) {
            return false
        }

        if (id == "new") {
            addNote(title, content)
        } else {
            updateNote(id, title, content)
        }

        return true
    }

    private fun addNote(title: String, content: String) {
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content
        )
        viewModelScope.launch {
            flow {
                emit(noteUseCases.addNote(newNote))
            }
                .catch { it.printStackTrace() }
                .collect { /* Success */ }
        }
    }

    private fun updateNote(id: String, title: String, content: String) {
        viewModelScope.launch {
            val currentNoteState = _currentNote.value
            if (currentNoteState is UiState.Success && currentNoteState.data != null) {
                val updatedNote = currentNoteState.data.copy(
                    title = title,
                    content = content
                )
                flow {
                    emit(noteUseCases.deleteNote(id))
                    emit(noteUseCases.addNote(updatedNote))
                }
                    .catch { it.printStackTrace() }
                    .collect { /* Success */ }
            }
        }
    }

    fun deleteNote(id: String) {
        if (id == "new") return

        viewModelScope.launch {
            flow { emit(noteUseCases.deleteNote(id)) }
                .catch { it.printStackTrace() }
                .collect { /* Success */ }
        }
    }
}