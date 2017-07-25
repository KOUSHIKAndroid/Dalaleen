package com.dalaleen.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dalaleen.logger.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by su on 12/16/16.
 */

public class NetworkConnection {

    public static String LOG_TAG="NetworkConnection";
    public static Context context;

    public NetworkConnection(Context context){
        this.context=context;
    }


    public static boolean netCheck(){
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                connected = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                connected = true;
            }
        } else {
            // not connected to the internet
            connected = false;
        }
        return connected;
    }


    public static boolean isInternetAccessible() {
        if (netCheck()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(2000);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Logger.showError(LOG_TAG, "Couldn't check internet connection"+ e);
            }
        } else {
            Log.d(LOG_TAG, "Internet not available!");
        }
        return false;
    }
}
