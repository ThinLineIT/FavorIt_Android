package com.thinlineit.favorit_android.android.data

sealed class Result<T> {
    data class Loading<T>(val isLoading: Boolean) : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Fail<T>(val exception: Exception) : Result<T>()
}
