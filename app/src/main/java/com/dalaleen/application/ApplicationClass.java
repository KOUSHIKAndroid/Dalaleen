package com.dalaleen.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

import static com.dalaleen.helper.NetworkConnection.context;


/**
 * Created by su on 4/3/17.
 */

public class ApplicationClass extends Application {

    public static String phoneNumber="";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "63fPQGziHO1G5QVXcRvNUaUZZ";
    public static final String TWITTER_SECRET = "FFycTD82OKpNM5ocLVQwW47tVDzs6LvrzpUULRCeXjumoDa5ha";

    public static final String LanguageCode="ar";

    public String countryCode="";
    private static ApplicationClass instance=null;
    public static final String TAG = ApplicationClass.class.getSimpleName();
    private RequestQueue mRequestQueue;
    public static SharedPreferences Loginsharedpreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LanguageCode)); // API 17+ only.
        res.updateConfiguration(conf, dm);


        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        instance=this;
        Loginsharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
    }



    public static ApplicationClass getInstance(){
        return instance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}