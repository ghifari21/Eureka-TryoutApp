package com.gosty.tryoutapp.data.models

import java.util.TimeZone

data class ScoreModel(
    val scoreId: String,
    val testOrder: Int,
    val correct: Int,
    val mistake: Int,
    val notAnswered: Int,
    val time: TimeZone,
    val grade: Int,
)
