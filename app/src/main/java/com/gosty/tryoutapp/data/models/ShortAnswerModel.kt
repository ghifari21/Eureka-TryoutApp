package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShortAnswerModel(
	val secondRange: String? = null,
	val shortAnswerText: String? = null,
	val firstRange: String? = null,
	val questionId: Int? = null,
	val idShortAnswer: Int? = null
) : Parcelable