package com.example.simplenoteapp.core.utils

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val code: Int) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}