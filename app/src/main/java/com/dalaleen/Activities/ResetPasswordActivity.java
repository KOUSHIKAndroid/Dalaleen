package com.dalaleen.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.dalaleen.R;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.ConstantClass;
import com.dalaleen.helper.CustomAlert;
import com.dalaleen.helper.CustomAlertForBackground;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.helper.MyCustomAlertBackgroundListener;
import com.dalaleen.helper.MyCustomAlertListener;
import com.dalaleen.logger.Logger;

import org.json.JSONObject;

/**
 * Created by su on 4/19/17.
 */

public class ResetPasswordActivity extends AppCompatActivity implements MyCustomAlertListener,MyCustomAlertBackgroundListener {
    ProgressDialog progDailog;
    EditText edit_otp_no,edit_new_password,edit_confirm_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edit_otp_no= (EditText) findViewById(R.id.edit_otp_no);
        edit_new_password= (EditText) findViewById(R.id.edit_new_password);
        edit_confirm_password= (EditText) findViewById(R.id.edit_confirm_password);


        progDailog= new ProgressDialog(ResetPasswordActivity.this);
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_otp_no.getText().toString().trim().equals("")){
                    findViewById(R.id.error_otp_no).setVisibility(View.VISIBLE);
                }
                else {
                    findViewById(R.id.error_otp_no).setVisibility(View.GONE);
                    if(edit_new_password.getText().toString().trim().equals("")){
                        findViewById(R.id.error_enter_your_new_password).setVisibility(View.VISIBLE);
                    }
                    else {
                        findViewById(R.id.error_enter_your_new_password).setVisibility(View.GONE);

                        if(edit_confirm_password.getText().toString().trim().equals("")){
                            findViewById(R.id.error_confirm_password).setVisibility(View.VISIBLE);
                        }
                        else {
                            findViewById(R.id.error_confirm_password).setVisibility(View.GONE);
                           if (!edit_new_password.getText().toString().trim().equals(edit_confirm_password.getText().toString().trim())){
                               findViewById(R.id.error_confirm_password).setVisibility(View.VISIBLE);
                               ((LatoRegular)findViewById(R.id.error_confirm_password)).setText("Confirm password should be same to the new password");
                           }
                           else {
                               findViewById(R.id.error_confirm_password).setVisibility(View.GONE);

                               String  url=""+ ConstantClass.BASE_URL+"/Jsonapp_control/resetpassword?otp_code="+edit_otp_no.getText().toString().trim()+"&password="+edit_new_password.getText().toString().trim()+"&lang=en";

                               CustomAsynctask customAsynctask=new CustomAsynctask(ResetPasswordActivity.this,progDailog);
                               customAsynctask.getResultListenerFromAsynctaskForGet(url, new CustomAsynctask.onAPIResponse() {
                                   @Override
                                   public void onSuccess(String result) {
                                       Logger.showInfo("result in callback",""+result);
                                       try {
                                           if (new JSONObject(result).getString("response").equalsIgnoreCase("TRUE")){

                                               CustomAlert customAlert = new CustomAlert(ResetPasswordActivity.this, "Reset password",new JSONObject(result).getString("message"), ResetPasswordActivity.this);
                                               customAlert.getListenerOKCancelFromNormalAlert();
                                           }
                                           else {
                                               CustomAlertForBackground customAlertForBackground=new CustomAlertForBackground(ResetPasswordActivity.this,"Reset password",new JSONObject(result).getString("message"),ResetPasswordActivity.this);
                                               customAlertForBackground.getListenerOKCancelFromNormalAlertBackground();
                                           }
                                       } catch (Exception e) {
                                           e.printStackTrace();
                                       }
                                   }

                                   @Override
                                   public void onError(String Error) {

                                   }
                               });
                           }
                        }
                    }
                }
            }
        });


    }



    @Override
    public void callbackForAlert(String result) {

        Logger.showInfo("result",""+result);

        if(result.equals("ok")){
            finish();
        }

    }

    @Override
    public void callbackForBackgroundAlert(String result) {

        Logger.showInfo("result",""+result);

    }
}
