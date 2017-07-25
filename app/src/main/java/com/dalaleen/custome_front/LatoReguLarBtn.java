package com.dalaleen.custome_front;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by su on 27/7/15.
 */
public class LatoReguLarBtn extends AppCompatButton {

    public LatoReguLarBtn(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public LatoReguLarBtn(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LatoReguLarBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "Lato-Regular.ttf"));

    }
}