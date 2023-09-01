package com.gosty.tryoutapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubjectModel(
	val tryout: List<TryoutModel?>? = null,
	val subjectName: String? = null,
	val idSubject: Int? = null
) : Parcelable