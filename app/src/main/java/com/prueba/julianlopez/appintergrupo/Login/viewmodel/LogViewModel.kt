package com.prueba.julianlopez.appintergrupo.Login.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log
import com.prueba.julianlopez.appintergrupo.Login.bussines.repository.LogRepository

class LogViewModel(application: Application) : AndroidViewModel(application) {

    private var logBaseRepository:LogRepository? = null

    init {
        logBaseRepository = LogRepository(application.applicationContext)
    }

    fun getListLog():LiveData<List<Log>>{

        return logBaseRepository!!.getLogs()
    }

    fun saveLog(log: Log){
        logBaseRepository!!.saveLog(log)
    }
}