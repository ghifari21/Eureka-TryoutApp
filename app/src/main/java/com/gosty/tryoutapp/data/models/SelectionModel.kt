package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionModel(
	val image: String? = null,
	val idSelection: Int? = null,
	val selectionText: String? = null,
	val questionId: Int? = null
) : Parcelable