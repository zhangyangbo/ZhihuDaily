package com.example.ex00_day_con;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
	
	public static void setNoPic(Context context, boolean isNoPic) {
		SharedPreferences sp = context.getSharedPreferences("setting", 0);
		sp.edit().putBoolean("isNoPic", isNoPic).commit();
	}
	public static boolean isNoPic(Context context) {
		SharedPreferences sp = context.getSharedPreferences("setting", 0);
		boolean isNoPic = sp.getBoolean("isNoPic", false);
		return isNoPic;
	}
	
	public static boolean isBigText(Context context) {
		SharedPreferences sp = context.getSharedPreferences("setting", 0);
		boolean isBigText = sp.getBoolean("isBigText", false);
		return isBigText;
	}

	public static void setBigText(Context context, boolean isBigText) {
		SharedPreferences sp = context.getSharedPreferences("setting", 0);
		sp.edit().putBoolean("isBigText", isBigText).commit();
	}
	
	public static boolean readMode(Context context) {
		SharedPreferences sp = context.getSharedPreferences("setting", 0);
		boolean isDark = sp.getBoolean("dark_mode", false);
		return isDark;
	}

	public static void saveMode(Context context, boolean isDark) {
		SharedPreferences sp = context.getSharedPreferences("setting", 0);
		sp.edit().putBoolean("dark_mode", isDark).commit();
	}

}
