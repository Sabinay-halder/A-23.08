package com.widevision.avartan.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.widevision.avartan.bean.GsonClass;
import com.widevision.avartan.util.Constants;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by mercury-one on 23/9/15.
 */
public class GetSubeventForm extends QueryManager<GsonClass> {
    String subeventId;

    public GetSubeventForm(String subeventId) {

        this.subeventId = subeventId;


    }

    @Override
    protected Request.Builder buildRequest() {
        RequestBody formBody = new FormEncodingBuilder().add("tag", "get_form").add("subeventId", subeventId)
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

