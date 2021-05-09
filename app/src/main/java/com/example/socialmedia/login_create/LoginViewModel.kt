package com.example.socialmedia.login_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.firebase_service.FirebaseService
import com.example.socialmedia.utils.Utils
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    val id = MutableLiveData("")
    val pwd = MutableLiveData("")
    private val _loginState = MutableLiveData<LoginState>(LoginState.Default)
    val loginState: LiveData<LoginState> = _loginState
    val progress = MutableLiveData(false)

    fun login(text: String) {
        val userId = id.value.toString()
        val userPwd = pwd.value.toString()

        if (userId.isEmpty()) {
            _loginState.value = LoginState.Failed("User ID & Pwd fields cannot be empty")
            return
        }
        if (userPwd.isEmpty()) {
            _loginState.value = LoginState.Failed("User ID & Pwd fields cannot be empty")
            return
        }
        progress.value = true
        viewModelScope.launch {
            if (text == "Create User") {
                Utils.logger("LoginViewModel", "Creating User..")
                firebaseService.createUser(userId, userPwd) { result ->
                    handleResult(result)
                }
            } else {
                Utils.logger("LoginViewModel", "Logging in User..")
                firebaseService.loginWithIdAndPwd(userId, userPwd) { result ->
                    handleResult(result)
                }
            }
        }
    }

    private fun handleResult(result: Result<FirebaseUser>) {
        progress.postValue(false)
        when (result) {
            is Result.Success -> {
                _loginState.value = LoginState.Success(result.data.uid)
            }
            is Result.Failed -> {
                _loginState.value = LoginState.Failed(result.message)
            }
            else -> {
            }
        }
    }
}