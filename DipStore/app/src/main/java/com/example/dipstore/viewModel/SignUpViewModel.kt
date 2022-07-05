package com.example.dipstore.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dipstore.interfaces.ApiCallBack
import com.example.dipstore.interfaces.UserInterface
import com.example.dipstore.model.ErrorResponse
import com.example.dipstore.model.SignInSuccessResponse
import com.example.dipstore.model.SignupSuccessResponse
import org.json.JSONObject

class SignUpViewModel: BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val successResponse: MutableLiveData<SignupSuccessResponse> =  MutableLiveData()
    val errorResponse: MutableLiveData<ErrorResponse> =  MutableLiveData()

    fun addUser(email: String, password: String) {
        val retrofitData =
            UserInterface.retrofitObject().create(UserInterface::class.java)
                .addUserData(email,password)
        request(retrofitData, object : ApiCallBack {
            override fun <T> onSuccess(data: T) {
                successResponse.postValue(data as SignupSuccessResponse)
            }

            override fun onFailure(error: ErrorResponse) {
                errorResponse.postValue(error)
            }
        })
    }

    fun createUser(url: String, body: JSONObject? = null) {
        request(url, "POST", SignupSuccessResponse::class.java, body, 200, object : ApiCallBack {

            override fun <T> onSuccess(data: T) {
                successResponse.postValue(data as SignupSuccessResponse)
            }

            override fun onFailure(error: ErrorResponse) {
                Log.d("Response", error.toString())
                errorResponse.postValue(error)
            }
        })
    }
}