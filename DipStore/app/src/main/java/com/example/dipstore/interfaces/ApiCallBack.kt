package com.example.dipstore.interfaces

import com.example.dipstore.model.ErrorResponse

interface ApiCallBack<T> {
    fun onSuccess(data: T)
    fun onFailure(error: ErrorResponse)
}