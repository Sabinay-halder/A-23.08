package com.widevision.avartan.bean;

import java.util.ArrayList;

/**
 * Created by mercury-one on 19/9/15.
 */
public class Event {
    public String eventId;
    public String eventName;
    public String img;
    public ArrayList<subevent> subevent;
    public class subevent {
        public String subeventId;
        public String subeventName;
    }
}
