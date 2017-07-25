package com.dalaleen.custome_front;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class LatoBoldTV extends AppCompatTextView {

	public LatoBoldTV(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public LatoBoldTV(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LatoBoldTV(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {

		super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
				"Lato-Bold.ttf"));

	}




	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		if(focused)
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void onWindowFocusChanged(boolean focused) {
		if(focused)
			super.onWindowFocusChanged(focused);
	}


	@Override
	public boolean isFocused() {
		return true;
	}
}