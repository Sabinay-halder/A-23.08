package com.widevision.avartan.activity;

//http://saharareporters.com/feeds/news/feed

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.widevision.avartan.R;
import com.widevision.avartan.bean.GsonClass;
import com.widevision.avartan.dao.EventRegistration;
import com.widevision.avartan.dao.GetTabList;
import com.widevision.avartan.model.CustomButton;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.util.AsyncCallback;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;
import com.widevision.avartan.util.NetworkUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class SubEventFragmentActivity extends Activity {
    public static String subEventId, subEventName;

    private EventDetailsFragment mEventDetailsFragment;
    private TimelineFragment mTimeLineFragment;
    private RulesFragment mrulesFragment;
    private PrizeFragment mPrizeFragment;
    private SubmissionCriteriaFragment mSubmissionCriteriaFragment;
    private RegistrationFragment mRegistrationFragment;
    private EventOutLineFragment mEventOutLineFragment;
    private CustomLoaderDialog progress;
    @Bind(R.id.text_event_outline)
    CustomButton mEventOutLine;
    @Bind(R.id.text_event_details)
    CustomButton mEventDetail;
    @Bind(R.id.text_timeline)
    CustomButton mTimeLine;
    @Bind(R.id.text_rules)
    CustomButton mRules;
    @Bind(R.id.text_prize)
    CustomButton mPrize;
    @Bind(R.id.text_submission_criteria)
    CustomButton mSubmissionCriteria;
    @Bind(R.id.text_register)
    CustomButton mRegister;
    @Bind(R.id.titleText)
    TextView titleTxt;
    @Bind(R.id.back)
    ImageView mBackImage;
    String detail;
    public static ArrayList<GsonClass.tab_array> tab_arrays;
    private static ArrayList<GsonClass.detail_array> delail_arrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub_event_fragment);
        ButterKnife.bind(this);
        subEventId = getIntent().getExtras().getString("SubEventId");
        subEventName = getIntent().getExtras().getString("SubEventName");
        titleTxt.setText(subEventName);
        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SubEventFragmentActivity.this.finish();
            }
        });
        progress = new CustomLoaderDialog(SubEventFragmentActivity.this);
        progress.showDialog();
/*call GetTabList for tabs*/
        GetTabList getTabList = new GetTabList(subEventId);
        getTabList.query(new AsyncCallback<GsonClass>() {
            @Override
            public void onOperationCompleted(GsonClass result, Exception e) {
                progress.closeDialog();
                if (e == null && result != null) {
                    if (result.success.equals("1")) {
                        tab_arrays = result.data.tab_array;
                        delail_arrays = result.data.detail_array;
                        mEventDetailsFragment = new EventDetailsFragment();

                        setTabs();
                        mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                        mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                        mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                        mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                        mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                        mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                        mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                        mEventDetail.setTextColor(getResources().getColor(R.color.white));
                        mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                        mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                        mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                        mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                        mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                        mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.add(R.id.container, mEventDetailsFragment).commit();

                    } else {
                        Constants.alert(SubEventFragmentActivity.this, "error.");
                        finish();
                    }
                }
            }
        });


        mEventDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                mEventDetail.setTextColor(getResources().getColor(R.color.white));
                mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mEventDetailsFragment = new EventDetailsFragment();
                fragmentTransaction.replace(R.id.container, mEventDetailsFragment);
                fragmentTransaction.commit();

            }
        });

        mTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                mTimeLine.setTextColor(getResources().getColor(R.color.white));
                mEventDetail.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mTimeLineFragment = new TimelineFragment();
                fragmentTransaction.replace(R.id.container, mTimeLineFragment);
                fragmentTransaction.commit();

            }
        });

        mRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                mRules.setTextColor(getResources().getColor(R.color.white));
                mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventDetail.setTextColor(getResources().getColor(R.color.gray_verylight));
                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mrulesFragment = new RulesFragment();
                fragmentTransaction.replace(R.id.container, mrulesFragment);
                fragmentTransaction.commit();

            }
        });

        mPrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                mPrize.setTextColor(getResources().getColor(R.color.white));
                mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventDetail.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mPrizeFragment = new PrizeFragment();
                fragmentTransaction.replace(R.id.container, mPrizeFragment);
                fragmentTransaction.commit();

            }
        });

        mSubmissionCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.white));
                mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventDetail.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mSubmissionCriteriaFragment = new SubmissionCriteriaFragment();
                fragmentTransaction.replace(R.id.container, mSubmissionCriteriaFragment);
                fragmentTransaction.commit();

            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_selected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));

                mRegister.setTextColor(getResources().getColor(R.color.white));
                mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventDetail.setTextColor(getResources().getColor(R.color.gray_verylight));
                mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventOutLine.setTextColor(getResources().getColor(R.color.gray_verylight));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mRegistrationFragment = new RegistrationFragment();
                fragmentTransaction.replace(R.id.container, mRegistrationFragment);
                fragmentTransaction.commit();

            }
        });
        mEventOutLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventDetail.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mTimeLine.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRules.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mSubmissionCriteria.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mRegister.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mPrize.setBackgroundColor(getResources().getColor(R.color.fragment_unselected));
                mEventOutLine.setBackgroundColor(getResources().getColor(R.color.fragment_selected));

                mEventOutLine.setTextColor(getResources().getColor(R.color.white));
                mTimeLine.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRules.setTextColor(getResources().getColor(R.color.gray_verylight));
                mSubmissionCriteria.setTextColor(getResources().getColor(R.color.gray_verylight));
                mRegister.setTextColor(getResources().getColor(R.color.gray_verylight));
                mPrize.setTextColor(getResources().getColor(R.color.gray_verylight));
                mEventDetail.setTextColor(getResources().getColor(R.color.gray_verylight));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                mEventOutLineFragment = new EventOutLineFragment();
                fragmentTransaction.replace(R.id.container, mEventOutLineFragment);
                fragmentTransaction.commit();

            }
        });

    }


    public void setTabs() {
        int size = tab_arrays.size();

        for (int i = 0; i < size; i++) {
            switch (tab_arrays.get(i).value) {
                case "Event Details":

                    mEventDetail.setVisibility(View.VISIBLE);
                    break;
                case "Timeline":

                    mTimeLine.setVisibility(View.VISIBLE);
                    break;
                case "Time Line":
                    mTimeLine.setVisibility(View.VISIBLE);
                    break;
                case "Rules":

                    mRules.setVisibility(View.VISIBLE);
                    break;
                case "Submission Criteria":

                    mSubmissionCriteria.setVisibility(View.VISIBLE);
                    break;
                case "Registration":

                    mRegister.setVisibility(View.VISIBLE);
                    break;
                case "Prize":

                    mPrize.setVisibility(View.VISIBLE);
                    break;
                case "Event Outline":

                    mEventOutLine.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }


    public static class EventDetailsFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.event_detail_fragment, container,
                    false);
            WebView eventDetailTxt = (WebView) view.findViewById(R.id.eventDetailTxt);

            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if (tab_arrays.get(i).value.equals("Event Details")) {

                    //   String detail = Html.fromHtml(delail_arrays.get(i).detail).toString();
                    String detail = delail_arrays.get(i).detail;
                    if (!detail.equals("null")) {
                        eventDetailTxt.loadData(detail, "text/html; charset=UTF-8", null);
                    } else {
                        eventDetailTxt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            return view;
        }
    }

    public static class EventOutLineFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.event_out_line_fragment, container,
                    false);
            WebView eventOutlineTxt = (WebView) view.findViewById(R.id.eventOutLineTxt);

            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if (tab_arrays.get(i).value.equals("Event Details")) {

                    //   String detail = Html.fromHtml(delail_arrays.get(i).detail).toString();
                    String detail = delail_arrays.get(i).detail;
                    if (!detail.equals("null")) {
                        eventOutlineTxt.loadData(detail, "text/html; charset=UTF-8", null);
                    } else {
                        eventOutlineTxt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            return view;
        }
    }


    public static class TimelineFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.timeline_fragment, container,
                    false);

            WebView eventDetailTxt = (WebView) view.findViewById(R.id.timelineTxt);

            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if ((tab_arrays.get(i).value.equals("Timeline")) || (tab_arrays.get(i).value.equals("Time Line"))) {

                    //   String detail = Html.fromHtml(delail_arrays.get(i).detail).toString();
                    String detail = delail_arrays.get(i).detail;

                    if (!detail.equals("null")) {
                        eventDetailTxt.loadData(detail, "text/html; charset=UTF-8", null);
                    } else {
                        eventDetailTxt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            return view;
        }
    }

    public static class RulesFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.rules_fragment, container,
                    false);

            WebView eventDetailTxt = (WebView) view.findViewById(R.id.rulesTxt);

            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if (tab_arrays.get(i).value.equals("Rules")) {

                    //   String detail = Html.fromHtml(delail_arrays.get(i).detail).toString();
                    String detail = delail_arrays.get(i).detail;

                    if (!detail.equals("null")) {
                        eventDetailTxt.loadData(detail, "text/html; charset=UTF-8", null);
                    } else {
                        eventDetailTxt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            return view;
        }
    }

    public static class PrizeFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.prize_fragment, container,
                    false);

            WebView eventDetailTxt = (WebView) view.findViewById(R.id.prizeTxt);

            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if (tab_arrays.get(i).value.equals("Prize")) {

                    //   String detail = Html.fromHtml(delail_arrays.get(i).detail).toString();
                    String detail = delail_arrays.get(i).detail;


                    if (!detail.equals("null")) {
                        eventDetailTxt.loadData(detail, "text/html; charset=UTF-8", null);
                    } else {
                        eventDetailTxt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            return view;
        }
    }

    public static class SubmissionCriteriaFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.submission_criteria_fragment, container, false);
            WebView eventDetailTxt = (WebView) view.findViewById(R.id.submissionCriteriaTxt);

            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if (tab_arrays.get(i).value.equals("Submission Criteria")) {

                    //   String detail = Html.fromHtml(delail_arrays.get(i).detail).toString();
                    String detail = delail_arrays.get(i).detail;


                    if (!detail.equals("null")) {
                        eventDetailTxt.loadData(detail, "text/html; charset=UTF-8", null);
                    } else {
                        eventDetailTxt.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            return view;
        }
    }

    public static class RegistrationFragment extends Fragment implements View.OnClickListener {

        private Extension ext;

      /*  @Bind(R.id.back)
        ImageView mBackButton;*/


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String newPattern = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        private CustomLoaderDialog dialog;
        private String collegeName, teamName, teamCaptainEmail, teamMemberEmail;
        private EditText mEditTeamName, mEditTeamCollegeName, mEditTeamCaptainEmail, mEditTeamMemberEmail;
        private TextView mregisterButton;
        private LinearLayout mContainerlayout;
        private ArrayList<GsonClass.fields> fieldses = new ArrayList<>();
        private PopupWindow pw;
        private String choice = "";

        private ArrayList<GsonClass.fields> registrationList = new ArrayList<>();

        //    private TextView mTitleText;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_event_registration_team, container,
                    false);
            final WebView registrationTxt = (WebView) view.findViewById(R.id.registrationTxt);
            //    final TextView registrationTxtUrl = (TextView) view.findViewById(R.id.registrationTxtUrl);
            dialog = new CustomLoaderDialog(getActivity());
            dialog.showDialog();
            ext = Extension.getInstance();
            mregisterButton = (TextView) view.findViewById(R.id.btn_register);
            mContainerlayout = (LinearLayout) view.findViewById(R.id.container);
            final LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent_registration);
            final LinearLayout registrationTxtLayout = (LinearLayout) view.findViewById(R.id.registrationTxtLayout);
            //   parent.setVisibility(View.VISIBLE);
            mregisterButton.setOnClickListener(this);
            registrationTxt.getSettings().setJavaScriptEnabled(true);
            registrationTxt.getSettings().setLoadWithOverviewMode(true);
            registrationTxt.getSettings().setUseWideViewPort(true);
            int size = tab_arrays.size();
            for (int i = 0; i < size; i++) {
                if (tab_arrays.get(i).value.equals("Registration")) {
                    final String detail = delail_arrays.get(i).detail;

                    if (subEventId.trim().equals("108")) {
                        registrationTxt.setWebViewClient(new WebViewClient(){

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                Log.e("","loading incomplete");
                                view.loadUrl(url);
                                dialog.closeDialog();
                                return true;
                            }
                            @Override
                            public void onPageFinished(WebView view, final String url) {

                                dialog.closeDialog();
                            }
                        });
                        registrationTxt.loadUrl("https://www.townscript.com/e/navkriti");
                        registrationTxtLayout.setVisibility(View.VISIBLE);
                        parent.setVisibility(View.GONE);
                        mregisterButton.setVisibility(View.GONE);
                    } else {


                        if (!detail.equals("null")) {


                            //      registrationTxt.loadData(detail, "text/html; charset=UTF-8", null);
                            registrationTxt.loadUrl(detail);
                            dialog.closeDialog();
                            registrationTxtLayout.setVisibility(View.VISIBLE);
                            parent.setVisibility(View.GONE);
                            mregisterButton.setVisibility(View.GONE);
                        } else {
                            registrationTxt.setVisibility(View.GONE);
                            registrationTxtLayout.setVisibility(View.GONE);
                            parent.setVisibility(View.VISIBLE);

                            RequestBody formBody = new FormEncodingBuilder().add("tag", "get_form").add("subeventId", subEventId)
                                    .build();
                            Request.Builder request = new Request.Builder();
                            request.url(Constants.mURL).post(formBody).build();

                            asyncQuery(request);
                        }

                    }
                    break;
                }
            }


            return view;
        }


        public void addView(final int i) {
            mregisterButton.setVisibility(View.VISIBLE);
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.event_registration_team_row, null);
            LinearLayout bgLayout = (LinearLayout) view.findViewById(R.id.bgLayout);
            final EditText editText = (EditText) view.findViewById(R.id.edt);
            editText.setHint(registrationList.get(i).label);
            if (registrationList.get(i).type.equals("email")) {
                bgLayout.setBackgroundResource(R.drawable.email);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                mContainerlayout.addView(view);
            } else if (registrationList.get(i).label.toUpperCase().contains("PHONE")) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                bgLayout.setBackgroundResource(R.drawable.phoneno);
                mContainerlayout.addView(view);
            } else if (registrationList.get(i).type.equals("text")) {
                if (registrationList.get(i).label.equals("College Name")) {
                    bgLayout.setBackgroundResource(R.drawable.collegename);
                } else if (registrationList.get(i).label.equals("Team Name")) {
                    bgLayout.setBackgroundResource(R.drawable.group);
                } else {
                    bgLayout.setBackgroundResource(R.drawable.events);
                }
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                mContainerlayout.addView(view);
            } else if (registrationList.get(i).type.toUpperCase().contains("SELECT")) {
                final View viewTxt = inflater.inflate(R.layout.event_registration_team_row_txt, null);
                final TextView textView = (TextView) viewTxt.findViewById(R.id.txt);
                textView.setHint(registrationList.get(i).label);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initiateDropDown(registrationList.get(i).choiceses, (TextView) view);
                    }
                });
                mContainerlayout.addView(viewTxt);
            }


            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    registrationList.get(i).value = editText.getText().toString().trim();
                }
            });


        }

        @Override
        public void onClick(View view) {
            validate();
        }

        public void validate() {
            boolean value = false;
            for (int i = 0; i < registrationList.size(); i++) {

                if (registrationList.get(i).type.equals("text")) {
                    if (registrationList.get(i).value != null) {
                        if (registrationList.get(i).value.length() == 0) {
                            value = false;
                            Constants.alert(getActivity(), registrationList.get(i).label + " required.");
                            break;
                        } else {
                            value = true;
                        }
                    } else {
                        value = false;
                        Constants.alert(getActivity(), registrationList.get(i).label + " required.");
                        break;
                    }
                } else if (registrationList.get(i).type.equals("email")) {
                    if (registrationList.get(i).value != null) {
                        if ((registrationList.get(i).value.length() == 0) || (!Constants.email_validation(registrationList.get(i).value))) {
                            value = false;
                            Constants.alert(getActivity(), registrationList.get(i).label + " not valid.");
                            break;
                        } else {
                            value = true;
                        }
                    } else {
                        value = false;
                        Constants.alert(getActivity(), registrationList.get(i).label + " required.");
                        break;
                    }
                } else if (registrationList.get(i).label.toUpperCase().contains("PHONE")) {
                    if (registrationList.get(i).value != null) {
                        if (registrationList.get(i).value.length() < 9) {
                            value = false;
                            Constants.alert(getActivity(), "enter valid phone number.");
                            break;
                        } else {
                            value = true;
                        }
                    } else {
                        value = false;
                        Constants.alert(getActivity(), registrationList.get(i).label + " required.");
                        break;
                    }
                } else if (registrationList.get(i).type.toUpperCase().contains("SELECT")) {
                    if (choice.length() == 0) {
                        Constants.alert(getActivity(), "Participation Module / Domain required.");
                        value = false;
                        break;
                    } else {
                        value = true;
                    }

                }
            }


            if (value) {
                try {
                    dialog.showDialog();
                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    for (int j = 0; j < registrationList.size(); j++) {
                        JSONObject object = new JSONObject();
                        object.put("field", registrationList.get(j).value);
                        jsonArray.put(object);
                    }
                    jsonObject.put("fields", jsonArray);

                    EventRegistration registration = new EventRegistration(subEventId, jsonObject.toString());
                    registration.query(new AsyncCallback<GsonClass>() {
                        @Override
                        public void onOperationCompleted(GsonClass result, Exception e) {
                            dialog.closeDialog();
                            if (result != null && e == null) {
                                if (result.success.equals("1")) {
                                    Constants.alert(getActivity(), "Registration done successfully.");
                                } else {
                                    Constants.alert(getActivity(), result.message);
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    dialog.closeDialog();
                    e.printStackTrace();
                }
            }
        }

        @SuppressWarnings("deprecation")
        private void initiateDropDown(ArrayList<GsonClass.choices> items, TextView view) {

            LinearLayout layout;

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.pop_up_window_radius, null);
            pw = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            pw.setTouchable(true);

            pw.setOutsideTouchable(true);
            pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

            pw.setTouchInterceptor(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        pw.dismiss();
                        return true;
                    }
                    return false;
                }
            });

            pw.setBackgroundDrawable(new BitmapDrawable());
            pw.setTouchable(true);
            pw.setOutsideTouchable(true);
            pw.setContentView(layout);
            pw.showAsDropDown(view);
            final ListView list = (ListView) layout.findViewById(R.id.dropdownlist);

            SpinnerListAdapter adapter = new SpinnerListAdapter(getActivity(), items, view);
            list.setAdapter(adapter);

        }

        class SpinnerListAdapter extends BaseAdapter {

            private ArrayList<GsonClass.choices> mListItems;
            private LayoutInflater mInflater;
            private ViewHolder holder;
            private TextView textView;

            public SpinnerListAdapter(Context context, ArrayList<GsonClass.choices> items, TextView textView) {
                this.mListItems = items;
                mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                this.textView = textView;
            }

            @Override
            public int getCount() {
                return mListItems.size();
            }

            @Override
            public Object getItem(int arg0) {
                return mListItems.get(arg0);
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.spinner_drop_down, null);
                    holder = new ViewHolder();
                    holder.tv = (TextView) convertView.findViewById(R.id.SelectOption);
                    holder.tv1 = (TextView) convertView.findViewById(R.id.SelectOption1);

                    convertView.setTag(holder);
                } else
                    holder = (ViewHolder) convertView.getTag();

                holder.tv.setText(mListItems.get(position).text);


                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        textView.setText(mListItems.get(position).text);
                        choice = mListItems.get(position).text;
                        pw.dismiss();

                    }
                });

                return convertView;
            }

            private class ViewHolder {
                TextView tv, tv1;

            }
        }

        public void asyncQuery(final Request.Builder requestBuilder) {


            OkHttpClient httpClient = new OkHttpClient();
            httpClient.setConnectTimeout(Constants.CONNECTION_SOCKET_TIMEOUT, TimeUnit.MINUTES);
            httpClient.setWriteTimeout(Constants.CONNECTION_SOCKET_TIMEOUT, TimeUnit.MINUTES);
            httpClient.setReadTimeout(Constants.CONNECTION_SOCKET_TIMEOUT, TimeUnit.MINUTES);
            httpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, final IOException e) {
                    Log.e("Request Failed", "Failed to get response for get request");

                    RegistrationFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.closeDialog();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    InputStream responseStream = response.body().byteStream();
                    final String responseString = NetworkUtils.readInputStream(responseStream);
                    Log.e("Request Passed", "Received response: " + responseString);
                    RegistrationFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.closeDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                String error = jsonObject.getString("error");
                                String success = jsonObject.getString("success");
                                if (success.trim().equals("1")) {
                                    JSONArray formArray = jsonObject.getJSONArray("form");


                                    JSONObject id = formArray.getJSONObject(0);  //form_data
                                    String subEventId = id.getString("subeventId");
                                    JSONObject formdata = id.getJSONObject("form_data");
                                    JSONArray fields = formdata.getJSONArray("fields");//"type": "text",

                                    for (int j = 0; j < fields.length(); j++) {
                                        JSONObject obj = fields.getJSONObject(j);
                                        String type = obj.getString("type");
                                        String ids = obj.getString("id");
                                        String label = obj.getString("label");

                                        GsonClass.fields item = new GsonClass.fields();
                                        item.type = type;
                                        item.label = label;

                                        if (type.trim().equals("select")) {
                                            ArrayList<GsonClass.choices> choiceses = new ArrayList<GsonClass.choices>();
                                            JSONArray choiceArray = obj.getJSONArray("choices");
                                            for (int k = 0; k < choiceArray.length(); k++) {
                                                JSONObject object = choiceArray.getJSONObject(k);
                                                String text = object.getString("text");
                                                GsonClass.choices choices = new GsonClass.choices();
                                                choices.text = text;
                                                choiceses.add(choices);
                                            }
                                            item.choiceses.addAll(choiceses);
                                        }
                                        registrationList.add(item);
                                    }
                                    for (int a = 0; a < registrationList.size(); a++) {
                                        addView(a);
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            });
        }

    }
}

