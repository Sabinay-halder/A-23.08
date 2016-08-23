package com.widevision.avartan.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.widevision.avartan.R;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.model.CustomTextView;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mercury-one on 23/9/15.
 */
public class SponserActivity extends Activity {


    public Extension ext;
    @Bind(R.id.titleText)
    CustomTextView mTitleText;
    @Bind(R.id.back)
    ImageView mBackImage;
    @Bind(R.id.webview_sponser)
    WebView myWebView;
    CustomLoaderDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        ButterKnife.bind(this);
        mTitleText.setText("Sponsors");
        ext = Extension.getInstance();
        progress = new CustomLoaderDialog(SponserActivity.this);
        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SponserActivity.this.finish();
            }
        });
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        if (ext.executeStrategy(SponserActivity.this, "", "internet")) {
            startWebView("http://avartan.nitie.org/index.php/sponsors/");
        }
        else{
            Constants.alert(SponserActivity.this, "No internet connectivity. ");
        }
    }


    private void startWebView(String url) {


            myWebView.setWebViewClient(new WebViewClient() {

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                //Show loader on url load
                public void onLoadResource(WebView view, String url) {

                }
               public void onPageFinished(WebView view, String url) {
                }
            });
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progre) {

                if (progre >= 90) {
                    progress.closeDialog();
                }

            }
        });

            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(url);
           progress.showDialog();
        }

}


