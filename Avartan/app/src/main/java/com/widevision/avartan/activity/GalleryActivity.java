package com.widevision.avartan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.widevision.avartan.R;
import com.widevision.avartan.bean.GsonClass;
import com.widevision.avartan.dao.GalleryDao;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.util.AsyncCallback;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 23/9/15.
 */
public class GalleryActivity extends Activity implements View.OnClickListener{
    private Extension ext;
    private String gallary_list;
    private int size;
    private CustomLoaderDialog progress;
ArrayList<GsonClass.Event> list;
    GalleryDao gallery;
    @Bind(R.id.custom_indicator)
    PagerIndicator mIndicator1;
    @Bind(R.id.custom_indicator2)
    PagerIndicator mIndicator2;
    @Bind(R.id.imageview)
    ImageView mGallery;
    @Bind(R.id.back)
    ImageView mBackImage;
    @Bind(R.id.gallaryTxtLayout)
    LinearLayout gallaryTxtLayout;
    @Bind(R.id.gallaryTxt)
    TextView gallaryTxt;
   @Bind(R.id.titleText)
   TextView mTitleText;
    @Bind(R.id.text_hr_images)
    TextView mhrImages;
    @Bind(R.id.text_it_images)
    TextView mItImages;
    @Bind(R.id.text_strategy_images)
    TextView mStrategyImages;
    @Bind(R.id.text_entreprenur_images)
    TextView mEntrepreneurImages;
    @Bind(R.id.text_finance_images)
    TextView mfinanceImages;
    @Bind(R.id.text_marketing_images)
    TextView mMarketingImages;
    @Bind(R.id.text_All_images)
    TextView mAllImages;
    @Bind(R.id.slider)
    SliderLayout mDemoSlider;
    private AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mAllImages.setTextColor(getResources().getColor(R.color.white));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        aQuery = new AQuery(GalleryActivity.this);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mTitleText.setText("Gallery");
        ext = Extension.getInstance();
        mAllImages.setOnClickListener(this);
        mEntrepreneurImages.setOnClickListener(this);
        mhrImages.setOnClickListener(this);
        mStrategyImages.setOnClickListener(this);
        mItImages.setOnClickListener(this);
        mMarketingImages.setOnClickListener(this);
        mfinanceImages.setOnClickListener(this);
        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(this);
        progress = new CustomLoaderDialog(GalleryActivity.this);
            progress.showDialog();
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {

            gallery = new GalleryDao(gallary_list);
            gallery.query(new AsyncCallback<GsonClass>() {

                @Override
                public void onOperationCompleted(GsonClass result, Exception e) {
                    progress.closeDialog();
                    if (result != null) {
                        if (result.success != null && result.success.equals("1")) {
                            list = result.event_list;
                            try {
                                mDemoSlider.removeAllSliders();
                                HashMap<String, String> url_maps = new HashMap<String, String>();
                                for (int i = 0; i < result.event_list.size(); i++) {
                                    size = result.event_list.get(i).gallaryList.size();
                                    for (int j = 0; j < size; j++) {
                                        gallaryTxtLayout.setVisibility(View.GONE);
                                        mGallery.setVisibility(View.GONE);
                                        mDemoSlider.setVisibility(View.VISIBLE);
                                        mIndicator1.setVisibility(View.VISIBLE);
                                        mIndicator2.setVisibility(View.VISIBLE);
                                        url_maps.put(result.event_list.get(i).gallaryList.get(j).gallaryName, result.event_list.get(i).gallaryList.get(j).gallaryImg);

                                    }
                                }

                                for (String name : url_maps.keySet()) {
                                    TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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
                            } catch (Exception d) {
                                d.printStackTrace();
                            }

                        }
                    }
                }

            });
        }
        else{
            progress.closeDialog();
            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }
        }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
    case R.id.text_All_images:

        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

        mAllImages.setTextColor(getResources().getColor(R.color.white));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));

        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {
                mDemoSlider.removeAllSliders();

                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    for (int j = 0; j < size; j++) {
                        gallaryTxtLayout.setVisibility(View.GONE);
                        mGallery.setVisibility(View.GONE);
                        mDemoSlider.setVisibility(View.VISIBLE);
                        mIndicator1.setVisibility(View.VISIBLE);
                        mIndicator2.setVisibility(View.VISIBLE);
                        url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);

                    }
                }

                for (String name : url_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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


            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }

        break;
    case R.id.text_hr_images:
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.white));
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {

                mDemoSlider.removeAllSliders();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    if (list.get(i).gallarycategoryName.equals("HR")) {
                        if (size == 0) {

                            gallaryTxtLayout.setVisibility(View.GONE);
                            mGallery.setVisibility(View.GONE);
                            mDemoSlider.setVisibility(View.GONE);
                            mIndicator1.setVisibility(View.GONE);
                            mIndicator2.setVisibility(View.GONE);
                        }
                        for (int j = 0; j < size; j++) {

                            if (size == 1) {
                                aQuery.id(mGallery).image(list.get(i).gallaryList.get(j).gallaryImg, true, true, 0, R.drawable.comingsoon);
                                gallaryTxt.setText(list.get(i).gallaryList.get(j).gallaryName);
                                gallaryTxtLayout.setVisibility(View.VISIBLE);
                                mGallery.setVisibility(View.VISIBLE);
                                mDemoSlider.setVisibility(View.GONE);
                                mIndicator1.setVisibility(View.GONE);
                                mIndicator2.setVisibility(View.GONE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                mIndicator1.setVisibility(View.VISIBLE);
                                mIndicator2.setVisibility(View.VISIBLE);
                                gallaryTxtLayout.setVisibility(View.GONE);
                                mGallery.setVisibility(View.GONE);
                                {
                                    url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);
                                }
                            }
                        }


                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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
            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }

        break;
    case R.id.text_finance_images:
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.white));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {
                mDemoSlider.removeAllSliders();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    if (list.get(i).gallarycategoryName.equals("Finance")) {
                        if (size == 0) {

                            gallaryTxtLayout.setVisibility(View.GONE);
                            mGallery.setVisibility(View.GONE);
                            mDemoSlider.setVisibility(View.GONE);
                            mIndicator1.setVisibility(View.GONE);
                            mIndicator2.setVisibility(View.GONE);
                        }
                        for (int j = 0; j < size; j++) {

                            if (size == 1) {
                                aQuery.id(mGallery).image(list.get(i).gallaryList.get(j).gallaryImg, true, true, 0, R.drawable.comingsoon);
                                gallaryTxt.setText(list.get(i).gallaryList.get(j).gallaryName);
                                gallaryTxtLayout.setVisibility(View.VISIBLE);
                                mGallery.setVisibility(View.VISIBLE);
                                mDemoSlider.setVisibility(View.GONE);
                                mIndicator1.setVisibility(View.GONE);
                                mIndicator2.setVisibility(View.GONE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                mIndicator1.setVisibility(View.VISIBLE);
                                mIndicator2.setVisibility(View.VISIBLE);
                                gallaryTxtLayout.setVisibility(View.GONE);
                                mGallery.setVisibility(View.GONE);
                                {
                                    url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);
                                }
                            }
                        }


                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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
            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }
        break;
    case R.id.text_entreprenur_images:
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.white));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {
                mDemoSlider.removeAllSliders();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    if (list.get(i).gallarycategoryName.equals("Entrepreneurship")) {
                        if (size == 0) {

                            gallaryTxtLayout.setVisibility(View.GONE);
                            mGallery.setVisibility(View.GONE);
                            mDemoSlider.setVisibility(View.GONE);
                            mIndicator1.setVisibility(View.GONE);
                            mIndicator2.setVisibility(View.GONE);
                        }
                        for (int j = 0; j < size; j++) {

                            if (size == 1) {
                                aQuery.id(mGallery).image(list.get(i).gallaryList.get(j).gallaryImg, true, true, 0, R.drawable.comingsoon);
                                gallaryTxt.setText(list.get(i).gallaryList.get(j).gallaryName);
                                gallaryTxtLayout.setVisibility(View.VISIBLE);
                                mGallery.setVisibility(View.VISIBLE);
                                mDemoSlider.setVisibility(View.GONE);
                                mIndicator1.setVisibility(View.GONE);
                                mIndicator2.setVisibility(View.GONE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                mIndicator1.setVisibility(View.VISIBLE);
                                mIndicator2.setVisibility(View.VISIBLE);
                                gallaryTxtLayout.setVisibility(View.GONE);
                                mGallery.setVisibility(View.GONE);
                                {
                                    url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);
                                }
                            }

                        }

                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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

            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }
        break;
    case R.id.text_it_images:
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.white));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {
                mDemoSlider.removeAllSliders();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    if (list.get(i).gallarycategoryName.equals("IT")) {
                        if (size == 0) {

                            gallaryTxtLayout.setVisibility(View.GONE);
                            mGallery.setVisibility(View.GONE);
                            mDemoSlider.setVisibility(View.GONE);
                            mIndicator1.setVisibility(View.GONE);
                            mIndicator2.setVisibility(View.GONE);
                        }
                        for (int j = 0; j < size; j++) {

                            if (size == 1) {
                                aQuery.id(mGallery).image(list.get(i).gallaryList.get(j).gallaryImg, true, true, 0, R.drawable.comingsoon);
                                gallaryTxt.setText(list.get(i).gallaryList.get(j).gallaryName);
                                gallaryTxtLayout.setVisibility(View.VISIBLE);
                                mGallery.setVisibility(View.VISIBLE);
                                mDemoSlider.setVisibility(View.GONE);
                                mIndicator1.setVisibility(View.GONE);
                                mIndicator2.setVisibility(View.GONE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                mIndicator1.setVisibility(View.VISIBLE);
                                mIndicator2.setVisibility(View.VISIBLE);
                                gallaryTxtLayout.setVisibility(View.GONE);
                                mGallery.setVisibility(View.GONE);
                                {
                                    url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);
                                }
                            }
                        }


                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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
            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }
        break;
    case R.id.text_marketing_images:
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mStrategyImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mMarketingImages.setTextColor(getResources().getColor(R.color.white));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {
                mDemoSlider.removeAllSliders();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    if (list.get(i).gallarycategoryName.equals("Marketing")) {
                        if (size == 0) {

                            gallaryTxtLayout.setVisibility(View.GONE);
                            mGallery.setVisibility(View.GONE);
                            mDemoSlider.setVisibility(View.GONE);
                            mIndicator1.setVisibility(View.GONE);
                            mIndicator2.setVisibility(View.GONE);
                        }
                        for (int j = 0; j < size; j++) {

                            if (size == 1) {
                                aQuery.id(mGallery).image(list.get(i).gallaryList.get(j).gallaryImg, true, true, 0, R.drawable.comingsoon);
                                gallaryTxt.setText(list.get(i).gallaryList.get(j).gallaryName);
                                gallaryTxtLayout.setVisibility(View.VISIBLE);
                                mGallery.setVisibility(View.VISIBLE);
                                mDemoSlider.setVisibility(View.GONE);
                                mIndicator1.setVisibility(View.GONE);
                                mIndicator2.setVisibility(View.GONE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                mIndicator1.setVisibility(View.VISIBLE);
                                mIndicator2.setVisibility(View.VISIBLE);
                                gallaryTxtLayout.setVisibility(View.GONE);
                                mGallery.setVisibility(View.GONE);
                                {
                                    url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);
                                }
                            }
                        }


                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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
            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }
        break;
    case R.id.text_strategy_images:
        mStrategyImages.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
        mhrImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mMarketingImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mEntrepreneurImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mfinanceImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mAllImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mItImages.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
        mStrategyImages.setTextColor(getResources().getColor(R.color.white));
        mAllImages.setTextColor(getResources().getColor(R.color.gray_verylight));

        mMarketingImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mItImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mEntrepreneurImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mfinanceImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        mhrImages.setTextColor(getResources().getColor(R.color.gray_verylight));
        if (ext.executeStrategy(GalleryActivity.this, "", "internet")) {
            try {
                mDemoSlider.removeAllSliders();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    size = list.get(i).gallaryList.size();
                    if (list.get(i).gallarycategoryName.equals("Strategy")) {
                        if (size == 0) {

                            gallaryTxtLayout.setVisibility(View.GONE);
                            mGallery.setVisibility(View.GONE);
                            mDemoSlider.setVisibility(View.GONE);
                            mIndicator1.setVisibility(View.GONE);
                            mIndicator2.setVisibility(View.GONE);
                        }
                        for (int j = 0; j < size; j++) {

                            if (size == 1) {
                                aQuery.id(mGallery).image(list.get(i).gallaryList.get(j).gallaryImg, true, true, 0, R.drawable.comingsoon);
                                gallaryTxt.setText(list.get(i).gallaryList.get(j).gallaryName);
                                gallaryTxtLayout.setVisibility(View.VISIBLE);
                                mGallery.setVisibility(View.VISIBLE);
                                mDemoSlider.setVisibility(View.GONE);
                                mIndicator1.setVisibility(View.GONE);
                                mIndicator2.setVisibility(View.GONE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                mIndicator1.setVisibility(View.VISIBLE);
                                mIndicator2.setVisibility(View.VISIBLE);
                                gallaryTxtLayout.setVisibility(View.GONE);
                                mGallery.setVisibility(View.GONE);
                                {
                                    url_maps.put(list.get(i).gallaryList.get(j).gallaryName, list.get(i).gallaryList.get(j).gallaryImg);
                                }
                            }
                        }

                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(GalleryActivity.this);
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


            } catch (Exception d) {
                d.printStackTrace();
            }
        }
        else{

            Constants.alert(GalleryActivity.this, "No internet connectivity. ");
        }
        break;
            case R.id.back:
                GalleryActivity.this.finish();
}

        }
        // }




}


