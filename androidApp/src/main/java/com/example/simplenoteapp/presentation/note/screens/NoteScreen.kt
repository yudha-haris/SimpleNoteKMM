package com.example.simplenoteapp.presentation.note.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplenoteapp.presentation.note.components.NoteItem
import com.example.simplenoteapp.presentation.note.viewmodels.NoteViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.simplenoteapp.core.utils.ResultState
import com.example.simplenoteapp.features.note.domain.model.Note

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = koinViewModel()
) {
    val notesState = viewModel.notes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        NoteInputField(
            onAddNote = { title, content -> viewModel.addNote(title, content) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        when (val state = notesState.value) {
            is ResultState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            is ResultState.Success -> NoteList(
                notes = state.data,
                onDelete = { id -> viewModel.deleteNote(id) }
            )
            is ResultState.Error -> Text(
                text = "Error: ${state.message}",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun NoteInputField(
    onAddNote: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    onAddNote(title, content)
                    title = ""
                    content = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }
    }
}

@Composable
fun NoteList(
    notes: List<Note>,
    onDelete: (String) -> Unit
) {
    LazyColumn {
        items(notes) { note ->
            NoteItem(note = note, onDelete = { onDelete(note.id) })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewNoteScreen() {
    NoteScreen()
}