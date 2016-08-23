package com.widevision.avartan.activity;

import android.content.Context;
import android.view.View;

import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.widget.base.BaseDialog;
import com.widevision.avartan.R;

import java.util.ArrayList;

/**
 * Created by mercury-one on 19/9/15.
 */
public class NormalListDialog extends BaseDialog {
    private ArrayList<DialogMenuItem> contents = new ArrayList<>();
    @Override
    public View onCreateView() {
        View child = getLayoutInflater().inflate(R.layout.normal_list_dialog, null);
        return child;
    }

    @Override
    public boolean setUiBeforShow() {
        return false;
    }

    public NormalListDialog(Context context) {
        super(context);
    }
    public NormalListDialog(Context context, String[] items) {
        super(context);
        this.contents = new ArrayList<DialogMenuItem>();
        for (String item : items) {
            DialogMenuItem customBaseItem = new DialogMenuItem(item, 0);
            contents.add(customBaseItem);
        }
    }
}

