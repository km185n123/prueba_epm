package com.prueba.julianlopez.appintergrupo.Login.bussines.model

class User(

    var isSuccess: Boolean,
    var authToken: String,
    var email: String,
    var password: String,
    var zone: String,
    var code:Int,
    var error:String,
    var remenber:Boolean

    ){

    constructor( email:String = "", password:String ="") : this(false,"",email,password,"",0,"",false)

}