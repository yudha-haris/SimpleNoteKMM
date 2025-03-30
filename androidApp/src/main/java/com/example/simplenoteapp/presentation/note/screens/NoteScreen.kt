package com.example.simplenoteapp.presentation.note.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplenoteapp.data.repository.InMemoryRepository
import com.example.simplenoteapp.domain.useCase.NoteUseCases
import com.example.simplenoteapp.presentation.note.components.NoteItem
import com.example.simplenoteapp.presentation.note.viewmodels.NoteViewModel

@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    val notes = viewModel.notes.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = {title = it},
            label = { Text("title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = content,
            onValueChange = {content = it},
            label = { Text("content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    viewModel.addNote(title, content)
                    title = ""
                    content = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(notes.value.size) { idx ->
                val note = notes.value.get(idx)
                NoteItem(note, onDelete = {
                    viewModel.deleteNote(note.id)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteScreen() {
    val repository = InMemoryRepository()
    val useCases = NoteUseCases.create(repository)
    val viewModel = NoteViewModel(useCases)
    NoteScreen(viewModel = viewModel)
}