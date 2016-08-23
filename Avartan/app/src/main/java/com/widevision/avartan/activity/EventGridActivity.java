package com.widevision.avartan.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.google.gson.Gson;
import com.widevision.avartan.R;
import com.widevision.avartan.bean.Event;
import com.widevision.avartan.model.CustomLoaderDialog;
import com.widevision.avartan.util.Constants;
import com.widevision.avartan.util.Extension;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;


public class EventGridActivity extends Activity {
    private CustomLoaderDialog progress;
    private EventAdapter mAdapter;
    private StaggeredGridView mgridview;

    public Event[] mEventObj;
    public Gson gson = new Gson();
    public Extension ext;
    @Bind(R.id.back)
    ImageView mBackImage;
    @Bind(R.id.titleText)
    TextView mTitleText;
    String file = Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_grid);
        ButterKnife.bind(this);
        mBackImage.setVisibility(View.VISIBLE);
        mBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EventGridActivity.this.finish();
            }
        });
        ext = Extension.getInstance();
        mgridview = (StaggeredGridView) findViewById(R.id.grid_view);
        progress = new CustomLoaderDialog(EventGridActivity.this);
        mTitleText.setText("Events");

        if (ext.executeStrategy(EventGridActivity.this, "", "internet")) {

            File file1 = new File(Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.filename);

                new ReadFile().execute();



        } else {
            Constants.alert(EventGridActivity.this, "No internet connectivity. ");
        }


    }


    private class ReadFile extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = Constants.readFileForFullPath(Environment.getExternalStorageDirectory() + "/" + Constants.Directory_path + "/" + Constants.filename);
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            String s = aVoid.toString();
            try {

                mEventObj = gson.fromJson(s, Event[].class);
                mAdapter = new EventAdapter(EventGridActivity.this, mEventObj);
                mgridview.setAdapter(mAdapter);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}

