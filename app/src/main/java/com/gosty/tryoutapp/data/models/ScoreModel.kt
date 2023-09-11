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
    val correctAnswer: Int? = null,
    val wrongAnswer: Int? = null,
    val notAnswered: Int? = null,
    val totalTime: Long? = null,
    val dateTime: Long? = null,
    val score: Int? = null,
    val tryoutCategory: String? = null,
    val answers: List<AnswerModel>? = null,
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "scoreId" to scoreId,
            "correctAnswer" to correctAnswer,
            "wrongAnswer" to wrongAnswer,
            "notAnswered" to notAnswered,
            "totalTime" to totalTime,
            "dateTime" to dateTime,
            "score" to score,
            "tryoutCategory" to tryoutCategory,
            "answers" to answers
        )
}
