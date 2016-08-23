package com.widevision.avartan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.widevision.avartan.R;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactUsActivity extends Activity {
    /* @Bind(R.id.contact_mail)
     EditText mcontactemail;
     @Bind(R.id.contact_name)
     EditText mContactName;
     @Bind(R.id.contact_message)
     EditText mContactMeassage;*/
    @Bind(R.id.back)
    ImageView mBackImage;
    @Bind(R.id.titleText)
    TextView mtitleText;
    @Bind(R.id.contact_url)
    TextView mContactUrl;
    @Bind(R.id.webview_contact)
    WebView myWebView;
    /*  @Bind(R.id.btn_submit)
      TextView mSubmitMessage;*/
    private Extension ext;
    // private CustomLoaderDialog dialog;
  /*  private String name,email,message;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";*/
    CustomLoaderDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        mtitleText.setText("Contact Us");
        ext = Extension.getInstance();
        progress = new CustomLoaderDialog(ContactUsActivity.this);

        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ContactUsActivity.this.finish();
            }
        });
        mContactUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ext.executeStrategy(ContactUsActivity.this, "", "internet")) {
                    myWebView.setVisibility(View.VISIBLE);
                    startWebView("http://avartan.nitie.org/");
                } else {
                    Constants.alert(ContactUsActivity.this, "No internet connectivity. ");
                }
            }

        });


    }

    private void startWebView(String url) {


        myWebView.setWebViewClient(new WebViewClient() {


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            public void onLoadResource(WebView view, String url) {




            }

            public void onPageFinished(WebView view, String url) {

            }

        });


        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(url);


    }


}
