package com.prueba.julianlopez.appintergrupo.Login.view.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import com.mobsandgeeks.saripaar.annotation.Pattern
import com.prueba.julianlopez.appintergrupo.Login.Util.ConstantsMessages
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.LoggedUser
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.User
import com.prueba.julianlopez.appintergrupo.Login.viewmodel.LoginViewModel
import com.prueba.julianlopez.appintergrupo.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() , Validator.ValidationListener{
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    val ACCESS_TOKEN = "accesToken"
    @NotEmpty(message = ConstantsMessages.OBLIGATORY_FIELD)
    @Pattern(regex = "[a-zA-Z0-9._-]+@[a-z]+.com",message = "Formato de correo no valido")
    @Email(message = ConstantsMessages.INVALID_EMAIL)
    lateinit var txtemail : AutoCompleteTextView

    @NotEmpty(message = ConstantsMessages.OBLIGATORY_FIELD)
    @Password
    private lateinit var txtPassword : EditText
    private lateinit var loginViewModel: LoginViewModel
    private var emailStr = ""
    private var passwordStr = ""
    private lateinit var loggedUser:LoggedUser
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle("LogDao")
        // Set up the login form.



        txtemail = email
        txtPassword = password
        setDataUser(emailStr,passwordStr)

         loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)




        var validator =  Validator(this)
        validator.setValidationListener(this)
        validateSession()

        email_sign_in_button.setOnClickListener {  validator.validate() }
    }
    private fun validateSession() {

        val loggedUser = loginViewModel.validateSession(loggedUser)

        if (loggedUser != null) {
            emailStr = loggedUser.loggedInUser!!.email
            passwordStr = loggedUser.loggedInUser!!.password
            REMENBER_CREDENTIALS = loggedUser.loggedInUser!!.remenber

            remenberSession.isChecked = REMENBER_CREDENTIALS

            if (REMENBER_CREDENTIALS){

                showProgress(true)
                setDataUser(emailStr,passwordStr)
                signIn(loggedUser)
                email_login_form.visibility = View.INVISIBLE

            }else{
                email_login_form.visibility = View.VISIBLE
            }
        }






    }

    private fun setDataUser(emailStr: String, passwordStr: String) {
        user = User(emailStr,passwordStr)
        loggedUser = LoggedUser(user)

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


    private fun attemptLogin() {


        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
         emailStr = txtemail.text.toString()
         passwordStr = txtPassword.text.toString()

        user.email = emailStr
        user.password = passwordStr
        user.remenber = remenberSession.isChecked
        loggedUser.loggedInUser = user

       signIn(loggedUser)


            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)

    }

    private fun signIn(loggedUser: LoggedUser) {

        loginViewModel.signIn(loggedUser).observe(this, Observer {
            if (it != null) {


                if (!it.loggedInUser!!.error.isEmpty()){

                    notificationError(it.loggedInUser!!.error)
                }else{
                    notificationSuccess(it.loggedInUser!!.authToken)
                }

            }
        })
    }

    /**
     * This method return a exception if it fails
     */

     fun notificationError(error: String) {

        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
        showProgress(false)
    }




    /**
     * This method allows you to go to the Dashboard
     */


    fun notificationSuccess(token: String) {



            val intent = Intent(this, DashBoardActivity::class.java)
            intent.putExtra(ACCESS_TOKEN,token)
            startActivity(intent)
            finish()




    }

    override fun onValidationSucceeded() {

        attemptLogin()

    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        if (errors != null) {
            for (error in errors) {
                val view = error.getView()
                val message = error.getCollatedErrorMessage(this)

                // Display error notification_layout ;)
                if (view is EditText) {
                    (view as EditText).error = message
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }


    companion object {

        private var REMENBER_CREDENTIALS = true

    }
}
