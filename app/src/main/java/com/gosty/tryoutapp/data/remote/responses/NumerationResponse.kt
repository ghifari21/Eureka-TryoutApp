package com.gosty.tryoutapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class NumerationResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItemResponse?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)