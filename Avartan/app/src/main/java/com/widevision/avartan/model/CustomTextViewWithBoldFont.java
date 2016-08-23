package com.widevision.avartan.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewWithBoldFont extends TextView {

	public CustomTextViewWithBoldFont(Context context) {
		super(context);

	}

	public CustomTextViewWithBoldFont(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CustomTextViewWithBoldFont(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {

		String otfName = "OpenSans-Regular.ttf";
		Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/"+ otfName);
		this.setTypeface(font,Typeface.BOLD);

//        // Get our custom attributes
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
//                R.styleable.CustomTextViewWithBoldFont, 0, 0);
//        try {
//            String typefaceName = a.getString(
//                    R.styleable.CustomTextViewWithBoldFont_typeface);
//            if (!isInEditMode() && !TextUtils.isEmpty(typefaceName)) {
//                Typeface typeface = sTypefaceCache.get(typefaceName);
//                if (typeface == null) {
//                    typeface = Typeface.createFromAsset(context.getAssets(),
//                            String.format("fonts/%s-Family.otf", typefaceName));
//// Cache the Typeface object
//                    sTypefaceCache.put(typefaceName, typeface);
//                }
//                setTypeface(typeface);
//// Note: This flag is required for proper typeface rendering
//                setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
//            }
//        } finally {
//            a.recycle();
//        }
    }

}
