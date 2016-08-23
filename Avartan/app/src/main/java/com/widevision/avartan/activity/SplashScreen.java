package com.widevision.avartan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.widevision.avartan.R;
import com.widevision.avartan.util.Constants;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 19/9/15.
 */
public class SplashScreen extends Activity {

    private final static int MSG_CONTINUE = 1234;
    private final static long DELAY = 2000;

    @Bind(R.id.logo)
    ImageView logo;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
        File dir = new File(Environment.getExternalStorageDirectory(), Constants.Directory_path);
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
        animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.slide_out_logo);


    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(MSG_CONTINUE);
        super.onDestroy();
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
}
