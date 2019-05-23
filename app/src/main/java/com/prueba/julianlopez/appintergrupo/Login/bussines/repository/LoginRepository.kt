package com.prueba.julianlopez.appintergrupo.Login.bussines.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.prueba.julianlopez.appintergrupo.BuildConfig.ACCESS_TOKEN
import com.prueba.julianlopez.appintergrupo.Login.Util.DataCache
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.LoggedUser
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.User
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.ApiListener
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.RestApiAdapter
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api.UserServices
import com.prueba.julianlopez.appintergrupo.Login.view.ui.LoginActivity
import retrofit2.Response
import rx.Single

class LoginRepository(context: Context, userServices:UserServices) : BaseRepository() {

    private var userServices:UserServices? = null
    private var context:Context = context

    init {
        this.userServices = userServices
         this.context = context

    }

    fun signIn(loggedUser:LoggedUser):LiveData<LoggedUser> {
        val login:MutableLiveData<LoggedUser>  = MutableLiveData()
        val result: Single<Response<User>> = userServices!!.signIn( loggedUser.loggedInUser!!.email  , loggedUser.loggedInUser!!.password )
        RestApiAdapter.request(result, object: ApiListener<User> {
            override fun onSuccess(user: User?) {

                //loggedUser.loggedInUser = user

                if (user != null) {
                    loggedUser.loggedInUser!!.email = user.email
                    loggedUser.loggedInUser!!.authToken = user.authToken
                }

                setDataUserCache(context ,loggedUser)
                if (loggedUser.loggedInUser!!.remenber){

                    login.value = getDataUserCache(context,loggedUser)
                }else{

                    login.value = loggedUser
                }
            }

            override fun onError(throwable: Throwable) {

                Log.e("onError  ", throwable.message)
                throwable.printStackTrace()
                loggedUser.loggedInUser!!.error = throwable.message.toString()
                login.value = getDataUserCache(context,loggedUser)
            }

            override fun onServerError(errorMessage: String) {

                Log.e("onServerError ", errorMessage)
                loggedUser.loggedInUser!!.error = errorMessage
                login.value = getDataUserCache(context,loggedUser)
            }

        })



        return login
    }

    private fun getDataUserCache(context: Context,loggedUser: LoggedUser): LoggedUser? {

        var emailStr = DataCache.readData(context,"EMAIL")
        var  passwordStr = DataCache.readData(context,"PASSWORD")
        var  authToken = DataCache.readData(context,"token")
        var remenberCredencial = DataCache.readData(context,"remenber")

        loggedUser.loggedInUser!!.email = emailStr
        loggedUser.loggedInUser!!.password = passwordStr
        loggedUser.loggedInUser!!.remenber = remenberCredencial.toBoolean()
        loggedUser.loggedInUser!!.authToken = authToken
        return loggedUser
    }

    fun validateSession(loggedUser: LoggedUser): LoggedUser? {



        return getDataUserCache(context,loggedUser)

    }
    private fun setDataUserCache(context: Context, loggedUser: LoggedUser) {
        if (loggedUser != null) {

                DataCache.writeData(context, "remenber", ""+ loggedUser.loggedInUser!!.remenber)
                DataCache.writeData(context, "EMAIL", loggedUser.loggedInUser!!.email)
                DataCache.writeData(context, "PASSWORD", loggedUser.loggedInUser!!.password)
                DataCache.writeData(context, "token", loggedUser.loggedInUser!!.authToken)


        }

    }

    fun resetData() {

        DataCache.writeData(context, "EMAIL", "")
        DataCache.writeData(context, "PASSWORD", "")
        DataCache.writeData(context, "remenber", "false")
        DataCache.writeData(context, "token", "")

    }


}