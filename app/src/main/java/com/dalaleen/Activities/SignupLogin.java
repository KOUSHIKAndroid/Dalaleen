package com.dalaleen.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dalaleen.R;
import com.dalaleen.helper.CustomAlertForBackground;
import com.dalaleen.helper.MyCustomAlertBackgroundListener;
import com.dalaleen.logger.Logger;

public class SignupLogin extends AppCompatActivity implements MyCustomAlertBackgroundListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        findViewById(R.id.tv_log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i=new Intent(SignupLogin.this,LoginActivity.class);
                    startActivity(i);
                    finish();
            }
        });

        findViewById(R.id.tv_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(SignupLogin.this,ActivityDigitsVerification.class);
                    startActivity(intent);
            }
        });

        findViewById(R.id.tv_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignupLogin.this,BaseActivity.class);
                startActivity(i);
//                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        CustomAlertForBackground customAlertForBackground=new CustomAlertForBackground(SignupLogin.this,"Exit","Do you want to exit application?",this);
        customAlertForBackground.getListenerOKCancelFromNormalAlertBackground();
    }

    @Override
    public void callbackForBackgroundAlert(String result) {
        Logger.showInfo("result",""+result);

        if(result.equals("ok")){
            finish();
        }
    }
}
