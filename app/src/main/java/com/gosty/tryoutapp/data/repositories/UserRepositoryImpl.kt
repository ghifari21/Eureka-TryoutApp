package com.gosty.tryoutapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.gosty.tryoutapp.data.models.UserModel
import com.gosty.tryoutapp.utils.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val crashlytics: FirebaseCrashlytics
) : UserRepository {
    /***
     * This method to let user sign in using their Google Account.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     */
    override fun signIn(credential: AuthCredential): LiveData<Result<String>> {
        val result = MediatorLiveData<Result<String>>()
        result.value = Result.Loading

        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(UserRepositoryImpl::class.java.simpleName, "signIn: ${auth.currentUser?.displayName}")
                    result.value = Result.Success("Berhasil: ${auth.currentUser?.displayName}")
                } else {
                    Log.e(UserRepositoryImpl::class.java.simpleName, "signIn: ${it.exception}")
                    crashlytics.log(it.exception.toString())
                    result.value = Result.Error(it.exception.toString())
                }
            }
            .addOnFailureListener {
                Log.e(UserRepositoryImpl::class.java.simpleName, "signIn: ${it.message.toString()}")
                crashlytics.log(it.message.toString())
                result.value = Result.Error(it.message.toString())
            }

        return result
    }
}