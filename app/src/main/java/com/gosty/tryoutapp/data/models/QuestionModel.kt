package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
	val selection: List<SelectionModel?>? = null,
	val selectionAnswer: List<SelectionAnswerModel?>? = null,
	val shortAnswer: List<ShortAnswerModel?>? = null,
	val questionText: String? = null,
	val qtId: Int? = null,
	val id: Int? = null,
	val discussion: List<DiscussionModel?>? = null,
	val tryoutId: Int? = null,
	val questionId: Int? = null
) : Parcelable