package com.example.dipstore.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dipstore.interfaces.ApiCallBack
import com.example.dipstore.interfaces.UserInterface
import com.example.dipstore.model.ErrorResponse
import com.example.dipstore.model.SignInSuccessResponse
import com.example.dipstore.model.SignupSuccessResponse
import okhttp3.RequestBody
import org.json.JSONObject

class SignInViewModel: BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val successResponse: MutableLiveData<SignInSuccessResponse> =  MutableLiveData()
    val errorResponse: MutableLiveData<ErrorResponse> =  MutableLiveData()

    fun signInUser(requestBody: RequestBody) {
        val retrofitData =
            UserInterface.retrofitObject().create(UserInterface::class.java).signInData(requestBody)
        request(retrofitData, object : ApiCallBack {
            override fun <T> onSuccess(data: T) {
                successResponse.postValue(data as SignInSuccessResponse)
            }

            override fun onFailure(error: ErrorResponse) {
                errorResponse.postValue(error)
            }
        })
    }

    fun loginUser(url: String, body: JSONObject? = null) {
        request(url, "POST", SignInSuccessResponse::class.java, body, 200, object : ApiCallBack {

            override fun <T> onSuccess(data: T) {
                successResponse.postValue(data as SignInSuccessResponse)
            }

            override fun onFailure(error: ErrorResponse) {
                Log.d("Response", error.toString())
                errorResponse.postValue(error)
            }
        })
    }
}