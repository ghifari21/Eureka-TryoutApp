package com.gosty.tryoutapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class SelectionItemResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id_selection")
	val idSelection: Int? = null,

	@field:SerializedName("selection_text")
	val selectionText: String? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null
)