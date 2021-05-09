package com.example.socialmedia.login_create

sealed class LoginState {

    data class Success(val data: String) : LoginState()

    data class Failed(val message: String) : LoginState()

    object Loading : LoginState()

    object Default : LoginState()

}
