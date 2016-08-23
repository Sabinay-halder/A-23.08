package com.widevision.avartan.util;

import android.content.Context;

import com.widevision.avartan.abstractClass.ValidationTemplate;


public class Extension {
    public static ValidationTemplate vali;
    public static Extension ext;


    private Extension() {


    }

    public static Extension getInstance() {

        if (vali == null && ext == null) {
            if (vali == null && ext == null) {
                vali = new Implementation();
                ext = new Extension();
                return ext;
            } else {
                return ext;
            }
        } else {
            return ext;
        }


    }

    public boolean executeStrategy(Context context, String text_if_needed, String check_tag) {

        return vali.template(context, text_if_needed, check_tag);

    }

}
