package com.example.simplenoteapp.features.note.domain.useCase

import com.example.simplenoteapp.core.utils.ResultState
import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetNoteByIdUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: String): Note? = repository.getNoteById(id)
}