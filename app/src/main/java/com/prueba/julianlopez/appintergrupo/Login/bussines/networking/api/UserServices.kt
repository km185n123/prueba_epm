package com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api

import com.prueba.julianlopez.appintergrupo.Login.Rest.ConstantsRestApi
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

interface UserServices {


    @GET(ConstantsRestApi.END_LOGIN)
    fun updateDataProfile(
            @Query("email") email:String,
            @Query("password") password:String
    ): Call<User>

    @GET(ConstantsRestApi.END_LOGIN)
    fun signIn(
            @Query("email") email:String,
            @Query("password") password:String
    ): Single<Response<User>>

}