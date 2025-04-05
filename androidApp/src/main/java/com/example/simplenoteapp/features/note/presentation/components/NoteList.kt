package com.example.simplenoteapp.features.note.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenoteapp.features.note.domain.model.Note

@Composable
fun NoteList(
    notes: List<Note>,
    onNoteClick: (String) -> Unit,
    onDeleteNote: (String) -> Unit,
) {
    if (notes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No notes yet. Click + to add one",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notes.size) { idx ->
                val note = notes[idx]
                NoteCard(
                    note = note,
                    onClick = { onNoteClick(note.id) },
                    onDelete = { onDeleteNote(note.id) }
                )
            }
        }
    }
}