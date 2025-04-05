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

class NoteListViewModel(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _notes = MutableStateFlow<UiState<List<Note>>>(UiState.Loading)
    val notes: StateFlow<UiState<List<Note>>> get() = _notes

    fun initNotes() {
        viewModelScope.launch {
            _notes.value = UiState.Loading
            loadNotes()
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            flow {
                emit(noteUseCases.getNotes())
            }
                .catch {
                    _notes.value =
                        UiState.Error(message = it.message.toString(), code = it.hashCode())
                }
                .collect { _notes.value = UiState.Success(it) }
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