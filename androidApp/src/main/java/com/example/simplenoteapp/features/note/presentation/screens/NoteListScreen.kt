package com.example.simplenoteapp.features.note.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.simplenoteapp.core.ui.components.atom.shimmerLoading
import com.example.simplenoteapp.core.utils.UiState
import com.example.simplenoteapp.features.note.presentation.components.NoteList
import com.example.simplenoteapp.features.note.presentation.viewmodels.NoteListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onNoteClick: (String) -> Unit,
    onAddNewNote: () -> Unit,
    viewModel: NoteListViewModel = koinViewModel()
) {
    val notesState by viewModel.notes.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        viewModel.initNotes()
    }

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewNote,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush)
                .padding(padding)
        ) {
            PullToRefreshBox(
                isRefreshing = notesState is UiState.Loading,
                onRefresh = {
                    viewModel.initNotes()
                },
            ) {
                when (val state = notesState) {
                    is UiState.Loading -> {
                        LazyColumn {
                            items(5) {
                                Box(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .shimmerLoading(),
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Success -> {
                        NoteList(
                            notes = state.data,
                            onNoteClick = onNoteClick,
                            onDeleteNote = { viewModel.deleteNote(it) },
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
}