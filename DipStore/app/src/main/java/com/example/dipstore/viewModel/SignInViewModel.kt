package com.example.dipstore.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dipstore.interfaces.ApiCallBack
import com.example.dipstore.model.ErrorResponse
import com.example.dipstore.model.SignInSuccessResponse
import org.json.JSONObject

class SignInViewModel: BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val successResponse: MutableLiveData<SignInSuccessResponse> =  MutableLiveData()
    val errorResponse: MutableLiveData<ErrorResponse> =  MutableLiveData()

    fun loginUser(url: String, body: JSONObject? = null) {
        request(url, "POST", SignInSuccessResponse::class.java, body, 200, object : ApiCallBack<SignInSuccessResponse> {
            override fun onSuccess(data: SignInSuccessResponse) {
                Log.d("Response", data.toString())
                successResponse.postValue(data)
            }

            override fun onFailure(error: ErrorResponse) {
                Log.d("Response", error.toString())
                errorResponse.postValue(error)
            }
        })
    }
}