package com.prueba.julianlopez.appintergrupo.Login.bussines.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.LogDataBaseHelper


class LogRepository(context: Context): BaseRepository() {


    private var logDataBaseHelper: LogDataBaseHelper? = null

    init {
        logDataBaseHelper = LogDataBaseHelper(context)
    }

    fun getLogs():LiveData<List<Log>>{

        return logDataBaseHelper!!.getAll()
    }

    fun saveLog(log: Log){

        logDataBaseHelper!!.save(log)
    }

}