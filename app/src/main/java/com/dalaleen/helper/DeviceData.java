package com.dalaleen.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import com.dalaleen.Activities.BaseActivity;

/**
 * Created by apple on 28/04/17.
 */

public class DeviceData {

    public static int DeviceWidth(Activity context){
        Display display = (context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    public static int DeviceHeight(Activity context){
        Display display = (context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}
