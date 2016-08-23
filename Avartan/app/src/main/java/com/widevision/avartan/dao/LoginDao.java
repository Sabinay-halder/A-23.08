package com.widevision.avartan.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.widevision.avartan.bean.GsonClass;
import com.widevision.avartan.util.Constants;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by mercury-one on 29/9/15.
 */
public class LoginDao extends QueryManager<GsonClass> {

    String pippin_user_login, pippin_user_pass;

    public LoginDao(String pippin_user_login, String pippin_user_pass) {

        this.pippin_user_login = pippin_user_pass;
        this.pippin_user_pass = pippin_user_pass;

    }


    @Override
    protected Request.Builder buildRequest() {

        RequestBody formBody = new FormEncodingBuilder()
                .add("pippin_user_login", pippin_user_login).add("teamName", pippin_user_pass)
             .build();
        Request.Builder request = new Request.Builder();
        request.url(Constants.mURL).post(formBody).build();

        return request;
    }

    @Override
    protected GsonClass parseResponse(String jsonResponse) {
        Gson gson = new GsonBuilder().create();
        try {
            GsonClass agents = gson.fromJson(jsonResponse, GsonClass.class);
            return agents;
        } catch (Exception e) {
            return null;
        }
    }
}