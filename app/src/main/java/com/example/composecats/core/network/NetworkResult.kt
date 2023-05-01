package com.example.composecats.core.network

sealed class NetworkResult<out T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error(val messageText: String) : NetworkResult<Nothing>()
}
