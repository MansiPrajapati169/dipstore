package com.example.dipstore.interfaces

import com.example.dipstore.model.SignInSuccessResponse
import com.example.dipstore.model.SignupSuccessResponse
import com.example.dipstore.utils.Constants.BASE_URL
import com.example.dipstore.utils.Constants.SIGN_IN_URL
import com.example.dipstore.utils.Constants.SIGN_UP_URL
import com.example.dipstore.utils.ErrorInterceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserInterface {

    @FormUrlEncoded
    @POST(SIGN_UP_URL)
    fun addUserData(@Field("email") email: String, @Field("password") password: String): retrofit2.Call<SignupSuccessResponse>

    @POST(SIGN_IN_URL)
    fun signInData(@Body request: RequestBody): retrofit2.Call<SignInSuccessResponse>

    companion object {
        fun retrofitObject(): Retrofit {
            val builder = OkHttpClient.Builder()
                .addInterceptor(ErrorInterceptor()).build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}