package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import java.util.TimeZone

@IgnoreExtraProperties
@Parcelize
data class ScoreModel(
    val scoreId: String? = null,
    val testOrder: Int? = null,
    val correctAnswer: Int? = null,
    val wrongAnswer: Int? = null,
    val notAnswered: Int? = null,
    val time: Long? = null,
    val grade: Double? = null,
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "scoreId" to scoreId,
            "testOrder" to testOrder,
            "correctAnswer" to correctAnswer,
            "wrongAnswer" to wrongAnswer,
            "notAnswered" to notAnswered,
            "time" to time,
            "grade" to grade
        )
}
