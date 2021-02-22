package com.rojer_ko.mypayments.data.model

import com.google.gson.annotations.SerializedName

data class ErrorDTO(
	@SerializedName("error_code")
	val error_code: Int,
	@SerializedName("error_msg")
	val error_msg: String
)