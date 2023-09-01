package com.gosty.tryoutapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class DiscussionItemResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("discussion_text")
	val discussionText: String? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null,

	@field:SerializedName("id_discussion")
	val idDiscussion: Int? = null
)