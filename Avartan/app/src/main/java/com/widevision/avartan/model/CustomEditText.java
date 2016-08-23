package com.widevision.avartan.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.widevision.avartan.R;

public class CustomEditText extends EditText {

	public CustomEditText(Context context) {
		super(context);

	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {

        String otfName = "OpenSans-Regular.ttf";
		Typeface font = Typeface.createFromAsset(context.getAssets(), otfName);
		this.setTypeface(font);
       this.setBackgroundResource(R.drawable.edittext_focus_selector);
        this.setTextColor(Color.WHITE);
        this.setBackground(null);


	}

}
