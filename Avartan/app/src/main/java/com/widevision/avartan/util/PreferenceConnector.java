package com.widevision.avartan.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceConnector {
	public static final String PREF_NAME = "Dress'N Room";
	public static final int MODE = Context.MODE_PRIVATE;
    public static final String CATEGORY_FILE_DOWNLOADED_DATE="category_file_downloaded_data";
    public static final String WOMEN_CATEGORY_FILE_DOWNLOADED_DATE="women_category_file_downloaded_data";
    public static final String CHILDREN_CATEGORY_FILE_DOWNLOADED_DATE="children_category_file_downloaded_data";
    public static final String TITLE_MEN = "TITLE_MEN";
    public static final String TITLE_WOMEN = "TITLE_WOMEN";
    public static final String TITLE_CHILDREN = "TITLE_CHILDREN";
    public static final String FILE_DOWNLOAD_DATE="file_download_date";

	public static void writeInteger(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();

	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	public static void writeString(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();

	}

	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}

	public static void writeBoolean(Context context, String key, Boolean value) {
		getEditor(context).putBoolean(key, value).commit();

	}

	public static Boolean readBoolean(Context context, String key,
			Boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	public static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}

	public static void remove(Context context, String key) {
		getEditor(context).remove(key);

	}
}
