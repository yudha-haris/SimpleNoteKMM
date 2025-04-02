package com.example.simplenoteapp.features.note.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoteDTO(
    val id: Int,
    val title: String,
    val content: String,
)