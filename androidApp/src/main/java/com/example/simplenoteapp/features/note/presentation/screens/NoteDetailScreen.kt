package com.example.simplenoteapp.features.note.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import com.example.simplenoteapp.core.utils.UiState
import com.example.simplenoteapp.features.note.presentation.components.NoteEditContent
import com.example.simplenoteapp.features.note.presentation.viewmodels.NoteDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String,
    onNavigateBack: () -> Unit,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val noteState by viewModel.currentNote.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isNewNote by remember { mutableStateOf(noteId == "new") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val backgroundBrush = if (isDarkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.surface
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.background
            )
        )
    }

    LaunchedEffect(key1 = noteId) {
        viewModel.getNoteById(noteId)
    }

    LaunchedEffect(key1 = noteState) {
        when (val state = noteState) {
            is UiState.Success -> {
                if (state.data != null) {
                    title = state.data.title
                    content = state.data.content
                    isNewNote = false
                } else {
                    // New note
                    title = ""
                    content = ""
                    isNewNote = true
                }
            }
            else -> {}
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteNote(noteId)
                        showDeleteDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isNewNote) "New Note" else "Edit Note",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                actions = {
                    if (!isNewNote) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Note",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    IconButton(onClick = {
                        if (viewModel.saveNote(noteId, title, content)) {
                            onNavigateBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Note"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush)
                .padding(padding)
        ) {
            when (val state = noteState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is UiState.Success -> {
                    NoteEditContent(
                        title = title,
                        content = content,
                        onTitleChange = { title = it },
                        onContentChange = { content = it }
                    )
                }
                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}