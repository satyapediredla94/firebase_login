package com.example.socialmedia.firebase_service

import com.example.socialmedia.login_create.Result
import com.example.socialmedia.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseService(
    private val firebaseAuth: FirebaseAuth
) {

    fun loginWithIdAndPwd(id: String, pwd: String, onComplete : (Result<FirebaseUser>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(id, pwd)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Utils.logger("Firebase Service", "Success: ${task.result?.user?.uid}")
                    onComplete(Result.Success(firebaseAuth.currentUser!!))
                } else {
                    Utils.logger("Firebase Service", "Error: ${task.exception?.localizedMessage}")
                    onComplete(Result.Failed(task.exception?.localizedMessage ?: "Authentication Failed", null))
                }
            }
    }

    fun createUser(id: String, pwd: String, onComplete : (Result<FirebaseUser>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(id, pwd)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Utils.logger("Firebase Service", "Success: ${task.result}")
                    onComplete(
                        Result.Success(firebaseAuth.currentUser!!)
                    )
                } else {
                    Utils.logger("Firebase Service", "Error: ${task.exception?.localizedMessage}")
                    onComplete(
                        Result.Failed(
                            task.exception?.localizedMessage ?: "Authentication Failed",
                            null)
                    )
                }
            }
    }

}