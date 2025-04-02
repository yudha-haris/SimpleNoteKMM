package com.example.simplenoteapp.features.note.data.remote.mapper

import com.example.simplenoteapp.features.note.data.remote.dto.NoteDTO
import com.example.simplenoteapp.features.note.domain.model.Note

fun NoteDTO.toDomain(): Note {
    return Note(id.toString(), title, content)
}

fun Note.toDTO(): NoteDTO {
    return NoteDTO(0, title, content)
}