package com.example.simplenoteapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.simplenoteapp.features.note.presentation.screens.NoteDetailScreen
import com.example.simplenoteapp.features.note.presentation.screens.NoteListScreen

object Destinations {
    const val NOTE_LIST = "note_list"
    const val NOTE_DETAIL = "note_detail"
    const val NOTE_ID_ARG = "noteId"

    const val DEEP_LINK_URI = "simplenoteapp://notes"
}

@Composable
fun NoteNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.NOTE_LIST
    ) {
        // Note list screen
        composable(Destinations.NOTE_LIST) {
            NoteListScreen(
                onNoteClick = { noteId ->
                    navController.navigate("${Destinations.NOTE_DETAIL}/$noteId")
                },
                onAddNewNote = {
                    navController.navigate("${Destinations.NOTE_DETAIL}/new")
                },
            )
        }

        // Note detail screen with argument and deep link support
        composable(
            route = "${Destinations.NOTE_DETAIL}/{${Destinations.NOTE_ID_ARG}}",
            arguments = listOf(
                navArgument(Destinations.NOTE_ID_ARG) {
                    type = NavType.StringType
                    defaultValue = "new"
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${Destinations.DEEP_LINK_URI}/{${Destinations.NOTE_ID_ARG}}"
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString(Destinations.NOTE_ID_ARG) ?: "new"
            NoteDetailScreen(
                noteId = noteId,
                onNavigateBack = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)
                    navController.popBackStack()
                }
            )
        }
    }
}