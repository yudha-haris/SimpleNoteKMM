package com.example.simplenoteapp.core.utils

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String, val code: Int) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}