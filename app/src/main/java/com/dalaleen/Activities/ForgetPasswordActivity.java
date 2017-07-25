package com.dalaleen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.dalaleen.R;
import com.dalaleen.helper.ConstantClass;
import com.dalaleen.helper.CustomAlert;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.helper.MyCustomAlertListener;
import com.dalaleen.logger.Logger;

import org.json.JSONObject;

/**
 * Created by su on 4/19/17.
 */

public class ForgetPasswordActivity extends AppCompatActivity implements MyCustomAlertListener {
    EditText edit_phone_no;
    ProgressDialog progDailog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edit_phone_no = (EditText) findViewById(R.id.edit_phone_no);

        progDailog = new ProgressDialog(ForgetPasswordActivity.this);
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_phone_no.getText().toString().trim().equals("")) {
                    findViewById(R.id.error_phone_no).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.error_phone_no).setVisibility(View.GONE);

                    String url = "" + ConstantClass.BASE_URL + "/Jsonapp_control/forgotpassword?phone=" + edit_phone_no.getText().toString() + "&lang=en";

                    CustomAsynctask customAsynctask = new CustomAsynctask(ForgetPasswordActivity.this, progDailog);
                    customAsynctask.getResultListenerFromAsynctaskForGet(url, new CustomAsynctask.onAPIResponse() {
                        @Override
                        public void onSuccess(String result) {
                            Logger.showInfo("result in callback", "" + result);


                            try {
                                if (new JSONObject(result).getString("response").equalsIgnoreCase("TRUE")) {

                                    Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    CustomAlert customAlert = new CustomAlert(ForgetPasswordActivity.this, "Forget password", new JSONObject(result).getString("message"), ForgetPasswordActivity.this);
                                    customAlert.getListenerOKCancelFromNormalAlert();
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
        });
    }


    @Override
    public void callbackForAlert(String result) {
        Logger.showInfo("result", "" + result);

        if (result.equals("ok")) {


        }

    }
}
