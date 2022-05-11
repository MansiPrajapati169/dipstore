package com.example.dipstore.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code()) {
            400 -> {
                 Log.d("Error","Bad Request")
            }
            401 -> {
                Log.d("Error","Unauthorized Error")
            }
            403 -> {
                // Show Forbidden Message
            }

            404 -> {
                // Show NotFound Message
            }
        }
        return response.newBuilder().header("Content-Type","application/json").build()
    }
}