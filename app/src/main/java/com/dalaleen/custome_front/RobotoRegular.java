package com.dalaleen.custome_front;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Bodhidipta on 13/06/16.
 */
public class RobotoRegular extends AppCompatTextView {

    public RobotoRegular(Context context) {
        super(context);
        init(context);
    }

    public RobotoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        super.setTypeface(FontCache.get("Roboto-Regular.ttf", context));
    }
}