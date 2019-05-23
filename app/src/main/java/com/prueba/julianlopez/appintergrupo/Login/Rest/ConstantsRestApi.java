package com.prueba.julianlopez.appintergrupo.Login.Rest;


import com.prueba.julianlopez.appintergrupo.BuildConfig;

public class ConstantsRestApi {

    // BASE URL
    public final static String ROOT_URL = BuildConfig.BASE_URL;

    //    http://directotesting.igapps.co/application/login
    public final static String END_LOGIN = "/application/login";
    public final static String AUTHENTICATION = ROOT_URL  + END_LOGIN;

    //    http://directotesting.igapps.co/application/login
    public final static String END_PROSPECTS = "/sch/prospects.json";
    public final static String PROSPECTS = ROOT_URL  + END_PROSPECTS;

}
