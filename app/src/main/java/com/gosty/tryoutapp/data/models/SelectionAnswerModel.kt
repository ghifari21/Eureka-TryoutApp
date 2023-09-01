package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionAnswerModel(
	val image: String? = null,
	val idAnswer: Int? = null,
	val selectionId: Int? = null,
	val questionId: Int? = null,
	val selectionText: String? = null
) : Parcelable