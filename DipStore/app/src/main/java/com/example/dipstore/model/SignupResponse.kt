package com.example.dipstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignupSuccessResponse (
      val id: Int? = null,
      val token: String? = null
): Parcelable

data class ErrorResponse (
    val error: String
)
