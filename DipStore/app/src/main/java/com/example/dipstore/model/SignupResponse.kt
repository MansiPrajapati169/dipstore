package com.example.dipstore.model

data class SignupSuccessResponse (
      val id: Int,
      val token: String
)

data class ErrorResponse (
    val error: String
)
