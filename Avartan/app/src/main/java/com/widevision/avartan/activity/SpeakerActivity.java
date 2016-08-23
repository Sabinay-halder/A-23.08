package com.widevision.avartan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.widevision.avartan.R;
import com.widevision.avartan.bean.GsonClass;
import com.widevision.avartan.dao.SpeakerDao;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.model.CustomTextView;
import com.widevision.avartan.util.AsyncCallback;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 23/9/15.
 */
public class SpeakerActivity extends Activity {
    public Extension ext;
    private String speak_persons;
    private CustomLoaderDialog progress;
    @Bind(R.id.titleText)
    CustomTextView mTitleText;
    @Bind(R.id.imageview)
    ImageView mSpeakerGallery;
    @Bind(R.id.slider)
    SliderLayout mDemoSlider;
    @Bind(R.id.custom_indicator)
    PagerIndicator mIndicator1;
    @Bind(R.id.custom_indicator2)
    PagerIndicator mIndicator2;
    @Bind(R.id.speakerTxtLayout)
    LinearLayout speakerTxtLayout;
    @Bind(R.id.speakerTxt)
    TextView speakerTxt;
    @Bind(R.id.back)
    ImageView mBackImage;
    private AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        ButterKnife.bind(this);
        ext = Extension.getInstance();
        aQuery = new AQuery(SpeakerActivity.this);
        mTitleText.setText("Speakers");
        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SpeakerActivity.this.finish();
            }
        });
        progress = new CustomLoaderDialog(SpeakerActivity.this);
        progress.showDialog();
        if (ext.executeStrategy(SpeakerActivity.this, "", "internet")) {
            SpeakerDao speaker = new SpeakerDao(speak_persons);
            speaker.query(new AsyncCallback<GsonClass>() {

                @Override
                public void onOperationCompleted(GsonClass result, Exception e) {
                    progress.closeDialog();
                    if (result != null) {
                        if (result.success != null && result.success.equals("1")) {
                            mDemoSlider.removeAllSliders();
                            HashMap<String, String> url_maps = new HashMap<String, String>();
                            for (int i = 0; i < result.event_list.size(); i++) {
                                if (result.event_list.size() == 1) {
                                    aQuery.id(mSpeakerGallery).image(result.event_list.get(i).img,true, true, 0, R.drawable.comingsoon);
                                    speakerTxt.setText(result.event_list.get(i).speakerName);
                                    mDemoSlider.setVisibility(View.GONE);
                                    mIndicator1.setVisibility(View.GONE);
                                    mIndicator2.setVisibility(View.GONE);
                                } else {
                                    speakerTxtLayout.setVisibility(View.GONE);
                                    mSpeakerGallery.setVisibility(View.GONE);
                                    {
                                        url_maps.put(result.event_list.get(i).speakerName, result.event_list.get(i).img);
                                    }
                                }
                            }

                            for (String name : url_maps.keySet()) {
                                TextSliderView textSliderView = new TextSliderView(SpeakerActivity.this);
                                // initialize a SliderLayout
                                textSliderView
                                        .description(name)
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.CenterInside);


                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);

                                mDemoSlider.addSlider(textSliderView);
                            }
                        }
                    }


                }

            });
        }
        else{
            progress.closeDialog();
            Constants.alert(SpeakerActivity.this, "No internet connectivity. ");
        }
    }


}
