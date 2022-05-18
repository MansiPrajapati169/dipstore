package com.example.dipstore.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dipstore.interfaces.ApiCallBack
import com.example.dipstore.model.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

open class BaseViewModel : ViewModel() {

    fun<T> request(
        url: String,
        method: String,
        dataClass: Class<T>,
        body: JSONObject? = null,
        requiredResponseCode: Int,
        apiCallBack: ApiCallBack<T>
    ) {
        val requestURL = URL(url)
        with(requestURL.openConnection() as HttpURLConnection) {
            requestMethod = method
            setRequestProperty("Content-type", "application/json")
            viewModelScope.launch(Dispatchers.IO) {
                body?.let {
                    outputStream.bufferedWriter().use {
                        it.write(body.toString())
                    }
                }
                if (responseCode == requiredResponseCode) {
                    val response = inputStream.bufferedReader().use {
                        it.readText()
                    }
                    Log.d("response", response)
                    val signUpData = Gson().fromJson(response, dataClass)
                    apiCallBack.onSuccess(signUpData)
                } else {
                    val response = errorStream.bufferedReader().use {
                        it.readText()
                    }
                    Log.d("error", response)
                    val signUpData = Gson().fromJson(response, ErrorResponse::class.java)
                    apiCallBack.onFailure(signUpData)
                }
            }
        }
    }
}
