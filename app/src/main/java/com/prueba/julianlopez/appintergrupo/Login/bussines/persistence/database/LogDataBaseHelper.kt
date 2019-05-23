package com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database

import android.arch.lifecycle.LiveData
import android.content.Context
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.dao.LogDao

class LogDataBaseHelper(context: Context) : BaseDatabaseHelper(context) {

    private var logDao:LogDao = appDatabase.LogDao()

    fun getAll():LiveData<List<Log>>{

        return logDao.getAll()
    }

    fun save(log : Log){
        logDao.insert(log)
    }


}