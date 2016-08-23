package com.widevision.avartan.bean;

import java.util.ArrayList;

/**
 * Created by androidone on 09/03/15.
 */
public class GsonClass {

    /*
    {"error":0,"success":1,"message":"Registration Successfully Done"}
     */
    public String success;
    public String message;
    public String found;

    //login activity
    public String email;
    public String user_id;

    public class Event {
        //for gallery
        public String gallarycategoryId;
        public String gallarycategoryName;
        public ArrayList<GallaryList> gallaryList;

        //for sponser
        public String sponsercategoryId;
        public String sponsercategoryName;
        public ArrayList<SponserList> sponserList;

        //for speaker
        public String speakerName;
        public String img;
        public String designation;
    }

    public class GallaryList {
        public String gallaryId;
        public String gallaryName;
        public String gallaryImg;

    }

    public class SponserList {
        public String sponserId;
        public String sponserName;
        public String sponserImg;

    }

    public ArrayList<Event> event_list;


    public ArrayList<form> form;

    public class form {
        public form_data form_data;
    }

    public class form_data {
        public ArrayList<fields> fields;
    }

    public static class fields {
        public String type;
        public String id;
        public String label;
        public String value;
        public ArrayList<choices> choiceses = new ArrayList<>();

    }

    public static class choices {
        public String text;
        public String value;
        public String isSelected;
        public String price;
    }


    /*for tab list*/
    public data data;
public String tag;
    public class data {
        public ArrayList<tab_array> tab_array;
        public ArrayList<detail_array> detail_array;
    }

    public class detail_array {
        public String detail;
    }

    public class tab_array {
        public String value;
    }
}
