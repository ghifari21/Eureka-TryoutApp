package com.gosty.tryoutapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class DataItemResponse(

	@field:SerializedName("tryout")
	val tryout: List<TryoutItemResponse?>? = null,

	@field:SerializedName("subject_name")
	val subjectName: String? = null,

	@field:SerializedName("id_subject")
	val idSubject: Int? = null
)