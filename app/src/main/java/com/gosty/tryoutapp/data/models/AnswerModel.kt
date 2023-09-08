package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class AnswerModel(
    val id: Int? = null,
    val tryoutId: Int? = null,
    val questionId: Int? = null,
    val essay: Boolean? = null,
    val answer: List<String>? = null,
    val correct: Boolean? = null
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "id" to id,
            "tryoutId" to tryoutId,
            "questionId" to questionId,
            "isEssay" to essay,
            "answer" to answer,
            "correct" to correct
        )
}
