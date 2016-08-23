package com.widevision.avartan.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.widevision.avartan.Loader.LoaderView;
import com.widevision.avartan.R;


/**
 * Created by androidone on 20/05/15.
 */
public class CustomLoaderDialog {

    Context context;
    Dialog dialog;
    LoaderView loaderView;
  // LoaderFactory loaderFactory;

    public CustomLoaderDialog(Context context) {
        this.context = context;
        createDialog();
    }


    /**
     * Display Dialog
     */

    public void createDialog() {
   //    loaderFactory.changeLoaderColor(null,Color.WHITE);
        dialog = new Dialog(context, android.R.style.Theme_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle("loading...");

        dialog.setCancelable(true);
        loaderView = (LoaderView) dialog.findViewById(R.id.imageLoader);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                loaderView.stopAnimation();
            }
        });
    }


    public void closeDialog() {
        loaderView.stopAnimation();
        dialog.dismiss();
    }



    public void showDialog() {
        loaderView.startAnimation();
        dialog.show();
    }
}
