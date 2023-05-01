package com.example.composecats.core.network

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Error(val messageText: String) : Result<Nothing>()
}
