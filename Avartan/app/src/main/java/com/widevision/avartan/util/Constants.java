package com.widevision.avartan.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.widevision.avartan.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mercury-one on 19/9/15.
 */
public class Constants {
    public static String Directory_path = "avartan";
    public static String filename = "eventlist1";
    public static String coupon=".s";
    public static String mEventFile = "http://avartan.nitie.org/app/event_list.json";
    public static String mURL = "http://avartan.nitie.org/app/getrequest.php";
  //  public static String mURLlocal = "http://103.231.44.154/avartan/app/getrequest.php";
    //    public static String mFormUrl = "http://192.168.0.50/avartan/app/event_list.php";
    public static int CONNECTION_SOCKET_TIMEOUT = 1;

    public static StringBuilder readFileForFullPath(String filenameWithDirectory) {

        StringBuilder offlinetext = new StringBuilder();


        File filepath = new File(filenameWithDirectory);

        if (filepath.exists()) {

            try {
                BufferedReader br = new BufferedReader(new FileReader(filepath), 8192);
                String line;

                while ((line = br.readLine()) != null) {
                    offlinetext.append(line);
                }
                br.close();
            } catch (Exception e) {
            }

        } else {
            Log.v("File Doesn't Exists!!!", ">>>>>>>>>");
        }

        return offlinetext;

    }

    public static String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month1 = month + 1;
        return (day + "-" + month1 + "-" + year);

    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {

        }

    }


    public static void alert(final Activity activity, final String msg) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView message = (TextView) dialog.findViewById(R.id.dialog_message);
        message.setText(msg);
        TextView okBtn = (TextView) dialog.findViewById(R.id.dialog_ok);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (msg.equals("Registration done successfully.")) {
                    activity.finish();
                }

            }
        });

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public static boolean email_validation(String text) {

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        Matcher emailMatcher = emailPattern.matcher(text);
        return emailMatcher.matches();
    }
    public static void downloadFileFromServer(String fileURL, String fileName) {

        StatFs stat_fs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double avail_sd_space = (double) stat_fs.getAvailableBlocks() * (double) stat_fs.getBlockSize();

        double MB_Available = (avail_sd_space / 10485783);

        Log.d("MB", "" + MB_Available);
        try {

            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            int fileSize = c.getContentLength() / 1048576;

            if (MB_Available <= fileSize) {
                c.disconnect();
                return;
            }

            try {
                FileOutputStream f = new FileOutputStream(new File(fileName));
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
            } catch (Exception e) {

                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
