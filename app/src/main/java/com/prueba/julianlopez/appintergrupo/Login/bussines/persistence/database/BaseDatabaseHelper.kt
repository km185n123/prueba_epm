package com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database

import android.content.Context

open class BaseDatabaseHelper(context:Context){

      val appDatabase: AppDatabase = AppDatabase.getInstance(context)

}