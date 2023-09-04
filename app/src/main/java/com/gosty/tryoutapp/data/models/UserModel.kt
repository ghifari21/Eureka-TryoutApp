package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "photoUrl" to photoUrl
        )
}
