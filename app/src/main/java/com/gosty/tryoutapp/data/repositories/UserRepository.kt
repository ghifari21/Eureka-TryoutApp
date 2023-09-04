package com.gosty.tryoutapp.data.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthCredential
import com.gosty.tryoutapp.utils.Result

interface UserRepository {
    fun signIn(credential: AuthCredential): LiveData<Result<String>>
}