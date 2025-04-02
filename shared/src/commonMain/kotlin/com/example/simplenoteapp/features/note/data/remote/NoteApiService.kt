package com.example.simplenoteapp.features.note.data.remote

import com.example.simplenoteapp.core.remote.ApiConstants
import com.example.simplenoteapp.features.note.data.remote.dto.NoteDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class NoteApiService(private val client: HttpClient) {
    suspend fun getNotes(): List<NoteDTO> {
        val response: HttpResponse = client.get(ApiConstants.GET_NOTES)
        return response.body()
    }

    suspend fun getNoteById(id: String): NoteDTO {
        return client.get(ApiConstants.GET_NOTES + "/$id").body()
    }

    suspend fun addNote(note: NoteDTO) {
        client.post(ApiConstants.GET_NOTES) {
            setBody(note)
        }
    }

    suspend fun deleteNote(id: String) {
        client.delete(ApiConstants.GET_NOTES + "/$id")
    }
}