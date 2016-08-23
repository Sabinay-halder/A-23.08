package com.widevision.avartan.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.widevision.avartan.R;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;
import com.widevision.avartan.util.PreferenceConnector;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 13/10/15.
 */
public class WinCouponActivity extends Activity {


    public Extension ext;
    @Bind(R.id.titleText)
    TextView mTitleText;
    @Bind(R.id.back)
    ImageView mBackImage;
    @Bind(R.id.webview_win_coupon)
    WebView myWebView;
    CustomLoaderDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_coupon);
        ButterKnife.bind(this);
        ext = Extension.getInstance();
        mBackImage.setVisibility(View.VISIBLE);
        progress = new CustomLoaderDialog(WinCouponActivity.this);

        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                WinCouponActivity.this.finish();
            }
        });
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        if (ext.executeStrategy(WinCouponActivity.this, "", "internet")) {
       startWebView("https://docs.google.com/forms/d/19_f_aet9gILnk3rqJKFoxKi05L63yqBQvJk6FGmjWEg/viewform");
          //  startWebView("https://docs.google.com/forms/d/1dt1nhNzhnZL3BzIIj6C1eRG64hGo9Fk3qWMOM70uXHU/viewform?usp=send_form");

        } else {
            Constants.alert(WinCouponActivity.this, "No internet connectivity. ");
        }
    }

    private void startWebView(String url) {


        myWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                // mTitleText.setText(url);
                try {
                    if ((url != null) && (url.trim().endsWith("formResponse"))) {
                        PreferenceConnector.writeString(getApplicationContext(), "couponsubmit", "yes");
                        File gift = new File(Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.coupon);
                        try {
                            gift.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(WinCouponActivity.this, "Your form has been submitted.", Toast.LENGTH_SHORT).show();
                        WinCouponActivity.this.finish();
                    }
                } catch (Exception e) {
                    PreferenceConnector.writeString(getApplicationContext(), "couponsubmit", "yes");
                    e.printStackTrace();
                    WinCouponActivity.this.finish();
                }
            }

            public void onPageFinished(WebView view, String url) {

            }

        });
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progre) {
                if (progre >= 80) {
                    progress.closeDialog();
                }

            }
        });


        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
        progress.showDialog();

    }

}


