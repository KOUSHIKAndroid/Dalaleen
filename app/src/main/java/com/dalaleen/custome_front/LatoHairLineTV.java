package com.dalaleen.custome_front;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class LatoHairLineTV extends AppCompatTextView {

	public LatoHairLineTV(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public LatoHairLineTV(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LatoHairLineTV(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {

		super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
				"Lato-Hairline.ttf"));

	}
}