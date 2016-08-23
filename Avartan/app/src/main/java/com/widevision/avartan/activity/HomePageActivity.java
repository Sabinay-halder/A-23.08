package com.widevision.avartan.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.widevision.avartan.R;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.PreferenceConnector;

import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 22/9/15.
 */
public class HomePageActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.image_coupon)
    ImageView mImageCoupon;

    @Bind(R.id.linear_layout_notifiacation)
    LinearLayout mLinearLayoutNotification;
    @Bind(R.id.linear_layout_events)
    LinearLayout mLinearLayoutEvents;
    @Bind(R.id.linear_layout_schedule)
    LinearLayout mLinearLayoutSchedule;
    @Bind(R.id.linear_layout_speaker)
    LinearLayout mLinearLayoutspeaker;
    @Bind(R.id.linear_layout_sponser)
    LinearLayout mLinearLayoutSponser;
    @Bind(R.id.linear_layout_gallery)
    LinearLayout mLinearLayoutGallery;
    @Bind(R.id.linear_layout_contact_us)
    LinearLayout mLinearLayoutContactUs;
    @Bind(R.id.linear_layout_nitie)
    LinearLayout mLinearLayoutNitie;
    @Bind(R.id.linear_layout_avartan)
    LinearLayout mLinearLayoutAvartan;

    @Bind(R.id.open_linear_layout_about)
    LinearLayout mOpenLinearLayoutAbout;
    @Bind(R.id.linear_layout_about)
    LinearLayout mLinearLayoutAbout;
    @Bind(R.id.layout)
    LinearLayout mUpperLayout;
    @Bind(R.id.logo)
    ImageView logo;

    public JSONParse jsonparse;
    Animation animation, fadeOut;

    int i = 0;
    Intent intent = new Intent();
    private String[] sectionHeaders;
    private final static int MSG_CONTINUE = 1234;
    private final static long DELAY = 2000;
    AnimationDrawable myAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_page);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mImageCoupon.setOnClickListener(this);
        mLinearLayoutAbout.setOnClickListener(this);
        mLinearLayoutNitie.setOnClickListener(this);
        mLinearLayoutAvartan.setOnClickListener(this);
        mLinearLayoutNotification.setOnClickListener(this);
        mLinearLayoutNotification.setVisibility(View.GONE);
        mLinearLayoutEvents.setOnClickListener(this);
        mLinearLayoutSchedule.setOnClickListener(this);
        mLinearLayoutspeaker.setOnClickListener(this);
        mLinearLayoutSponser.setOnClickListener(this);
        mLinearLayoutGallery.setOnClickListener(this);
        mLinearLayoutContactUs.setOnClickListener(this);
        String coupon=  PreferenceConnector.readString(getApplicationContext(),"couponsubmit","");

        File dir = new File(Environment.getExternalStorageDirectory(), Constants.Directory_path);
        File file2 = new File(Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.filename);
        File gift = new File(Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.coupon);

        if(coupon.equals("yes")){
            mImageCoupon.setVisibility(View.GONE);
         //   myAnimationDrawable.stop();
        }
        else
    {
        if(gift.exists()){
            mImageCoupon.setVisibility(View.GONE);
        }
        else {
            myAnimationDrawable
                    = (AnimationDrawable) mImageCoupon.getDrawable();
            mImageCoupon.post(
                    new Runnable() {

                        @Override
                        public void run() {
                            myAnimationDrawable.start();
                        }
                    });
        }

    }



        try {
            if (!dir.exists()) {
                dir.mkdir();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(file2.exists()){
                file2.delete();
                new JSONParse().execute();
            }
            else {

                new JSONParse().execute();
            }
        }catch (Exception d) {
            d.printStackTrace();
        }
        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
        animation = AnimationUtils.loadAnimation(HomePageActivity.this, R.anim.slide_out_logo);
        fadeOut = AnimationUtils.loadAnimation(HomePageActivity.this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mUpperLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mUpperLayout.startAnimation(fadeOut);
                logo.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void _continue() {
        /*startActivity(new Intent(this, HomePageActivity.class));
        finish();*/

        logo.startAnimation(animation);
    }

    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_CONTINUE:
                    _continue();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_coupon:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }
                    intent = new Intent(HomePageActivity.this, WinCouponActivity.class);
                    startActivity(intent);
                break;
            case R.id.linear_layout_about:
                if (i == 0) {
                    mOpenLinearLayoutAbout.setVisibility(View.VISIBLE);
                    i = 1;
                } else if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }

                break;


            case R.id.linear_layout_avartan:
                final Dialog dialog = new Dialog(HomePageActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_about_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.dialogTitlelayout);
               // layout.setBackgroundResource(R.drawable.bg_c);
                ImageView image = (ImageView) dialog.findViewById(R.id.dialogclose);
                TextView mtitle = (TextView) dialog.findViewById(R.id.dialogtitleText);
                mtitle.setText("About Avartan");
                mtitle.setTextColor(getResources().getColor(R.color.white));
                image.setVisibility(View.VISIBLE);
                WebView wv = (WebView) dialog.findViewById(R.id.webView);
                wv.setBackgroundColor(Color.TRANSPARENT);
                wv.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                dialog.show();

                wv.loadUrl("file:///android_asset/About_Avartan.html");
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.linear_layout_nitie:
                final Dialog mdialog = new Dialog(HomePageActivity.this);
                mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mdialog.setContentView(R.layout.custom_about_dialog);
                LinearLayout nlayout = (LinearLayout) mdialog.findViewById(R.id.dialogTitlelayout);

                mdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ImageView mimage = (ImageView) mdialog.findViewById(R.id.dialogclose);
                TextView mtitletext = (TextView) mdialog.findViewById(R.id.dialogtitleText);
                WebView mwv = (WebView) mdialog.findViewById(R.id.webView);
                mtitletext.setText("About NITIE");
                mtitletext.setTextColor(getResources().getColor(R.color.white));
                mwv.setBackgroundColor(Color.TRANSPARENT);
                mwv.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                mdialog.show();

                mwv.loadUrl("file:///android_asset/About_NITIE.html");
                mimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mdialog.dismiss();
                    }
                });

                break;
            case R.id.linear_layout_notifiacation:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }

                break;
            case R.id.linear_layout_events:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }

                intent = new Intent(HomePageActivity.this, EventGridActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_layout_schedule:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }

                intent = new Intent(HomePageActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_layout_speaker:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }
                intent = new Intent(HomePageActivity.this, SpeakerActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_layout_sponser:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }
                intent = new Intent(HomePageActivity.this, SponserActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_layout_gallery:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }
                intent = new Intent(HomePageActivity.this, GalleryActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_layout_contact_us:
                if (i == 1) {
                    mOpenLinearLayoutAbout.setVisibility(View.GONE);
                    i = 0;
                }
                    intent = new Intent(HomePageActivity.this, ContactUsActivity.class);
                    startActivity(intent);

                break;
        }

    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(MSG_CONTINUE);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String coupon=  PreferenceConnector.readString(getApplicationContext(),"couponsubmit","no");
        if(coupon.equals(("yes"))){
            mImageCoupon.setVisibility(View.GONE);
           // myAnimationDrawable.stop();

        }

    }

    private class JSONParse extends AsyncTask<String, String, Void> {


        @Override
        protected Void doInBackground(String... args) {

            Constants.downloadFileFromServer(Constants.mEventFile, Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.filename);
            return null;
        }

        protected void onPostExecute(JSONObject json) {

        }

    }

}
