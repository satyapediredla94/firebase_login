package com.example.socialmedia.di

import com.example.socialmedia.firebase_service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirebaseService(
        firebaseAuth: FirebaseAuth
    ) : FirebaseService = FirebaseService(firebaseAuth)

}