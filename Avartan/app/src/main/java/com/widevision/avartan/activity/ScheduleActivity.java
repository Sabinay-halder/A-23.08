package com.widevision.avartan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.widevision.avartan.R;
import com.widevision.avartan.model.CustomTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 22/9/15.
 */
public class ScheduleActivity extends Activity {
    @Bind(R.id.titleText) CustomTextView mTitleText;
    @Bind(R.id.back) ImageView mBackImage;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        mTitleText.setText("Schedule-2015");
        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ScheduleActivity.this.finish();

            }
        });
    }
}


