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
public class GalleryDao extends QueryManager<GsonClass> {
    String gallary_list;
    public GalleryDao(String gallary_list) {

        this.gallary_list = gallary_list;


    }
    @Override
    protected Request.Builder buildRequest() {
        RequestBody formBody = new FormEncodingBuilder().add("tag", "gallary_list")
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
