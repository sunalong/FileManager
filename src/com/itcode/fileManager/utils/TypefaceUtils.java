package com.itcode.fileManager.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypefaceUtils {

	private static Typeface OCTICONS;

	/**
	 * Set octicons typeface on given text view(s)<br>
	 * 将所给的TextView的字体设置成octicons字体
	 * 
	 * @param textViews
	 */
	public static void setOcticons(final TextView... textViews) {
		if (textViews == null || textViews.length == 0)
			return;

		/**
		 * 注意可获得context的不仅仅是activity等，还可以是view
		 */
		Typeface typeface = getOcticons(textViews[0].getContext());
		for (TextView textView : textViews)
			textView.setTypeface(typeface);
	}

	/**
	 * Get octicons typeface<br>
	 * 获取octicons【一种貌似牛逼的字体】字体
	 * 
	 * @param context
	 * @return octicons typeface
	 * @author sunalong
	 */
	public static Typeface getOcticons(final Context context) {
		if (OCTICONS == null)
			OCTICONS = getTypeface(context, "octicons-regular-webfont.ttf");
		return OCTICONS;
	}

	/**
	 * Get typeface with name<br>
	 * 根据给定的字体名称获取字体
	 * 
	 * @param context
	 * @param name
	 * @return typeface
	 * @author sunalong
	 */
	public static Typeface getTypeface(final Context context, final String name) {
		return Typeface.createFromAsset(context.getAssets(), name);
	}
}
