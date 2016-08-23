package com.widevision.avartan.activity;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.widevision.avartan.R;
import com.widevision.avartan.bean.Event;
import com.widevision.avartan.model.CustomTextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mercury-one on 16/9/15.
 */
public class EventAdapter extends BaseAdapter {


    private final Random mRandom;
    private AQuery aq;
    private BackgroundBlurDialog fragment = new BackgroundBlurDialog();
    private SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    public static ArrayList<Event.subevent> mSubEvent;
    public static String eventname = "";
    private Event[] data;
    private Activity context;

    public EventAdapter(Activity context, Event[] objects) {

        this.mRandom = new Random();
        this.data = objects;
        this.context = context;
        aq = new AQuery(context);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {

        final RelativeLayout relative = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relative.setLayoutParams(params);

        LinearLayout linear = new LinearLayout(context);
        linear.setGravity(Gravity.CENTER);
        linear.setBackgroundColor(Color.parseColor("#80000000"));
        ViewGroup.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(linearParams);

        CustomTextView eventTitle = new CustomTextView(context);
        ViewGroup.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        eventTitle.setLayoutParams(textParams);
        eventTitle.setGravity(Gravity.CENTER);
        eventTitle.setTextColor(Color.WHITE);
        eventTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        eventTitle.setText(data[position].eventName);
        linear.addView(eventTitle);


        params.addRule(0, RelativeLayout.ALIGN_PARENT_BOTTOM);


        double positionHeight = getPositionRatio(position);
        if (position == 0 ||position == 6) {
            MovingImageView imageView = new MovingImageView(context);
            aq.id(imageView).image(data[position].img,true, true, 0, R.drawable.comingsoon);
            imageView.setHeightRatio(positionHeight);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data[position].subevent != null && data[position].subevent.size() > 0) {
                        mSubEvent = data[position].subevent;
                       eventname=data[position].eventName;
                        fragment.show(context.getFragmentManager(), "SubEvent");
                    }

                }
            });
            relative.addView(imageView);
            relative.addView(linear);


        } else {
            DynamicHeightImageView imageView = new DynamicHeightImageView(context);
            aq.id(imageView).image(data[position].img,true, true, 0, R.drawable.comingsoon);
            imageView.setHeightRatio(positionHeight);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data[position].subevent != null && data[position].subevent.size() > 0) {
                        mSubEvent = data[position].subevent;
                        eventname=data[position].eventName;
                        fragment.show(context.getFragmentManager(), "SubEvent");
                    }
                }
            });

            relative.addView(imageView);
            relative.addView(linear);
        }

        return relative;
    }


    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);

        }


        return ratio;

    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
        // the width
    }

}
