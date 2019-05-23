package com.prueba.julianlopez.appintergrupo.Login.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.LoggedUser
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.RestApiAdapter
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api.ProspectsServices
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api.UserServices
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.ProspectDataBaseHelper
import com.prueba.julianlopez.appintergrupo.Login.bussines.repository.LoginRepository
import com.prueba.julianlopez.appintergrupo.Login.bussines.repository.ProspectRepository

class LoginViewModel(aplication: Application): AndroidViewModel(aplication) {

    private var loginRepository :LoginRepository = LoginRepository(aplication.applicationContext,RestApiAdapter().getService(UserServices::class.java))

    fun signIn(loggedUser:LoggedUser):LiveData<LoggedUser>{

        return loginRepository.signIn(loggedUser)
    }

     fun validateSession(loggedUser:LoggedUser): LoggedUser? {

         return loginRepository.validateSession(loggedUser)
     }

    fun resetData() {

        loginRepository.resetData()
    }


}