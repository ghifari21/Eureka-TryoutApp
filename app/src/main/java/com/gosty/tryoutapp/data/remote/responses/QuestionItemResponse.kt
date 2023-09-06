package com.gosty.tryoutapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class QuestionItemResponse(

    @field:SerializedName("selection")
	val selection: List<SelectionItemResponse?>? = null,

    @field:SerializedName("selection_answer")
	val selectionAnswer: List<SelectionAnswerItemResponse?>? = null,

    @field:SerializedName("short_answer")
	val shortAnswer: List<ShortAnswerItemResponse?>? = null,

    @field:SerializedName("question_text")
    val questionText: String? = null,

    @field:SerializedName("qt_id")
	val qtId: Int? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("discussion")
	val discussion: List<DiscussionItemResponse?>? = null,

    @field:SerializedName("tryout_id")
	val tryoutId: Int? = null,

    @field:SerializedName("question_id")
	val questionId: Int? = null
)