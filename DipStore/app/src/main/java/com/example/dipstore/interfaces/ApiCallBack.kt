package com.example.dipstore.interfaces

import com.example.dipstore.model.ErrorResponse

interface ApiCallBack {
    fun<T> onSuccess(data: T)
    fun onFailure(error: ErrorResponse)
}