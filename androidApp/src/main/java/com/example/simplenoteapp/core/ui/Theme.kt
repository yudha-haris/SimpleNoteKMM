package com.example.simplenoteapp.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light mode colors with bluish theme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2962FF),
    onPrimary = Color.White,
    secondary = Color(0xFF0039CB),
    onSecondary = Color.White,
    tertiary = Color(0xFF6F8BFF),
    background = Color(0xFFEEF2FF),
    surface = Color.White,
    onBackground = Color(0xFF1A1C1E),
    onSurface = Color(0xFF1A1C1E)
)

// Dark mode colors with bluish gradient theme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6F8BFF),
    onPrimary = Color.Black,
    secondary = Color(0xFF4564DB),
    onSecondary = Color.White,
    tertiary = Color(0xFF2962FF),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun SimpleNoteAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}