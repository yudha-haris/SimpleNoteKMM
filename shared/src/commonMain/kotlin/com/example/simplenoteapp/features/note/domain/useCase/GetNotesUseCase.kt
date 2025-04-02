package com.example.simplenoteapp.features.note.domain.useCase

import com.example.simplenoteapp.core.utils.Result
import com.example.simplenoteapp.features.note.domain.model.Note
import com.example.simplenoteapp.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): Flow<Result<List<Note>>> = flow {
        emit(Result.Loading)
        try {
            val notes = repository.getNotes()
            emit(Result.Success(notes))
        } catch (e: Exception) {
            emit(Result.Error(message = e.message.toString(), code = e.hashCode()))
        }
    }.flowOn(Dispatchers.Default)
}