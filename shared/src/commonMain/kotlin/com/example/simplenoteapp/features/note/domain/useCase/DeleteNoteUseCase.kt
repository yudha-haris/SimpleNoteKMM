package com.example.simplenoteapp.features.note.domain.useCase

import com.example.simplenoteapp.core.utils.Result
import com.example.simplenoteapp.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteNoteUseCase(private val repository: NoteRepository) {
    operator fun invoke(id: String) = flow {
        emit(Result.Loading)
        try {
            val notes = repository.deleteNote(id)
            emit(Result.Success(notes))
        } catch (e: Exception) {
            emit(Result.Error(message = e.message.toString(), code = e.hashCode()))
        }
    }.flowOn(Dispatchers.Default)
}