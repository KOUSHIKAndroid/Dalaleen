package com.dalaleen.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dalaleen.R;
import com.dalaleen.application.ApplicationClass;
import com.dalaleen.logger.Logger;

/**
 * Created by su on 3/29/17.
 */

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_splash);
        //Setting glide with Slpas gif
        Glide.with(getApplicationContext()).load(R.drawable.splashmain).into((ImageView) findViewById(R.id.main_splash));

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/


        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        ApplicationClass.getInstance().countryCode = tm.getNetworkCountryIso();
        Logger.showInfo("CountryCode", ApplicationClass.getInstance().countryCode);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                SharedPreferences shared = ApplicationClass.Loginsharedpreferences;

                if(shared.getString("user_id", "").equals("")) {
                    Intent i=new Intent(SplashActivity.this,SignupLogin.class);
                    startActivity(i);
                    finish();
                }
                else {

                    Intent intent=new Intent(SplashActivity.this,BaseActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
