package com.widevision.avartan.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.widevision.avartan.bean.GsonClass;
import com.widevision.avartan.util.Constants;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;


/**
 * Created by androidone on 09/03/15.
 */
public class RegistrationDao extends QueryManager<GsonClass> {

    String subeventId, teamName, captianEmail, collegeName, membersEmail;

    public RegistrationDao(String subeventId, String teamName, String captianEmail, String collegeName, String membersEmail) {

        this.subeventId = subeventId;
        this.teamName = teamName;
        this.captianEmail = captianEmail;
        this.collegeName = collegeName;
        this.membersEmail = membersEmail;

    }


    @Override
    protected Request.Builder buildRequest() {

        RequestBody formBody = new FormEncodingBuilder().add("tag", "event_registration")
                .add("subeventId", subeventId).add("teamName", teamName)
                .add("collegeName", collegeName).add("captianEmail", captianEmail)
                .add("membersEmail", membersEmail).build();
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
