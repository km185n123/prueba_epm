package com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database

import android.arch.lifecycle.LiveData
import android.content.Context
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.dao.ProspectDao

class ProspectDataBaseHelper(context: Context) : BaseDatabaseHelper(context) {

    private var prospectDao:ProspectDao = appDatabase.ProspectDao()

   fun getAll():LiveData<List<Prospect>>{
        return prospectDao.getAll()
    }

    fun save(prospects: List<Prospect>){
        prospectDao.insert(prospects)
    }

    fun update(prospect: Prospect){
        prospectDao.update(prospect)
    }

    fun cleanData(){
        prospectDao.nukeTable()
    }
}