package com.dalaleen.logger;

import android.util.Log;

/**
 * Created by su on 3/29/17.
 */

public class Logger {
    public static void showInfo(final String TAG_, final String MSG_) {
        Log.i(TAG_, MSG_);
    }

    public static void showError(final String TAG_, final String MSG_) {
        Log.e(TAG_, MSG_);
    }
}
