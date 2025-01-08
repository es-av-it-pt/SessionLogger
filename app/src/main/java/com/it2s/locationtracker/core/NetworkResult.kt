package com.it2s.locationtracker.core

sealed class NetworkResult<out T> {
    object Loading : NetworkResult<Nothing>()
    data class Success<T>(val result: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
}


