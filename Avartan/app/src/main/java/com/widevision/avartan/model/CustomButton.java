package com.widevision.avartan.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button {

	public CustomButton(Context context) {
		super(context);

	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		parseAttributes(context);
	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		parseAttributes(context);
	}

	private void parseAttributes(Context context) {
		String otfName = "OpenSans-Semibold.ttf";
		Typeface font = Typeface.createFromAsset(context.getAssets(),otfName);
		this.setTypeface(font,Typeface.BOLD);
	}

}
