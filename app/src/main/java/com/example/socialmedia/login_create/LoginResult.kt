package com.example.socialmedia.login_create

sealed class Result<T>(data: T? = null, message: String? = null) {

    data class Success<T>(val data : T) : Result<T>(data)

    data class Failed<T>(val message: String, val data: T?) : Result<T>(data, message)

    data class Loading<T>(val data: T?) : Result<T>(data)

}
