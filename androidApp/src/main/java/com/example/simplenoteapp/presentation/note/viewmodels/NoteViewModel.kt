package com.example.simplenoteapp.presentation.note.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenoteapp.core.utils.ResultState
import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.useCase.NoteUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID

class NoteViewModel(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _notes = MutableStateFlow<ResultState<List<Note>>>(ResultState.Loading)
    val notes: StateFlow<ResultState<List<Note>>> get() = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            flow {
                emit(noteUseCases.getNotes())
            }
                .catch {
                    _notes.value =
                        ResultState.Error(message = it.message.toString(), code = it.hashCode())
                }
                .collect { _notes.value = ResultState.Success(it) }
        }
    }

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title,
            content
        )
        viewModelScope.launch {
            flow {
                emit(noteUseCases.addNote(newNote))
            }
                .catch { it.printStackTrace() }
                .collect { loadNotes() }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            flow { emit(noteUseCases.deleteNote(id)) }
                .catch { it.printStackTrace() }
                .collect { loadNotes() }
        }
    }
}