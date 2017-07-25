package com.dalaleen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dalaleen.R;
import com.dalaleen.application.ApplicationClass;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.logger.Logger;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

/**
 * Created by su on 12/29/16.
 */

public class ActivityDigitsVerification extends AppCompatActivity {
    public static AuthCallback authCallback;
    ProgressDialog progDailog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digits);

        progDailog= new ProgressDialog(ActivityDigitsVerification.this);
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        // Clear session on logout
        Digits.clearActiveSession();

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(ApplicationClass.TWITTER_KEY, ApplicationClass.TWITTER_SECRET);
        Fabric.with(ActivityDigitsVerification.this, new TwitterCore(authConfig), new Digits.Builder().build());


        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                 //Do something with the session
                ApplicationClass.phoneNumber=phoneNumber.substring(3,phoneNumber.length());
                findViewById(R.id.auth_button).setVisibility(View.GONE);

                if(new NetworkConnection(ActivityDigitsVerification.this).netCheck()){
//                    checkNumberExist(ApplicationClass.phoneNumber);
                    Intent intent=new Intent(ActivityDigitsVerification.this,SignupActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    findViewById(R.id.linear_network_check_show).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.img_network_network_data)).setImageResource(R.drawable.ic_wifi_off);
                    ((LatoRegular)findViewById(R.id.network_check_show)).setText("Please Check Network Connection");
                }

            }
            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
                Logger.showInfo("DigitsException",exception.getMessage());
                finish();
            }
        };

        digitsButton.setCallback(authCallback);
        digitsButton.setAuthTheme(R.style.CustomDigitsThemeMaterial);


        AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                .withAuthCallBack(authCallback)
                .withPhoneNumber("+91");
        //Example of setting theme
        Digits.authenticate(authConfigBuilder.build());
        //Digits.authenticate(authConfigBuilder.build());
    }
}
