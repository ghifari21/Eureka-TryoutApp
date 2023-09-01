package com.gosty.tryoutapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class TryoutItemResponse(

    @field:SerializedName("subject_id")
	val subjectId: Int? = null,

    @field:SerializedName("category_name")
	val categoryName: String? = null,

    @field:SerializedName("category_id")
	val categoryId: Int? = null,

    @field:SerializedName("question")
	val question: List<QuestionItemResponse?>? = null,

    @field:SerializedName("subject_name")
	val subjectName: String? = null,

    @field:SerializedName("is_fav")
	val isFav: Boolean? = null,

    @field:SerializedName("id")
	val id: Int? = null
)