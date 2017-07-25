package com.dalaleen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dalaleen.R;
import com.dalaleen.application.ApplicationClass;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.ConstantClass;
import com.dalaleen.helper.CustomAlert;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.helper.MyCustomAlertListener;
import com.dalaleen.logger.Logger;

import org.json.JSONObject;

/**
 * Created by su on 4/10/17.
 */

public class VerificationLoginActivity extends AppCompatActivity implements MyCustomAlertListener {
    LatoRegular error_verify,tv_verify;
    EditText edit_verify;
    LinearLayout linear_layout_skip;
    ProgressDialog progDailog;

    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification_login);

        id= getIntent().getStringExtra("id");

        error_verify= (LatoRegular) findViewById(R.id.error_verify);
        tv_verify= (LatoRegular) findViewById(R.id.tv_verify);



        edit_verify= (EditText) findViewById(R.id.edit_verify);
        linear_layout_skip= (LinearLayout) findViewById(R.id.linear_layout_skip);


        progDailog= new ProgressDialog(VerificationLoginActivity.this);
        progDailog.setMessage("Verifying...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);


        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_verify.getText().toString().equals("")){
                    error_verify.setVisibility(View.VISIBLE);
                }else {
                    error_verify.setVisibility(View.GONE);

                    String url=""+ ConstantClass.BASE_URL+ ConstantClass.Activation+"?activation_code="+edit_verify.getText().toString();

                    CustomAsynctask customAsynctask=new CustomAsynctask(VerificationLoginActivity.this,progDailog);
                    customAsynctask.getResultListenerFromAsynctaskForGet(url, new CustomAsynctask.onAPIResponse() {
                        @Override
                        public void onSuccess(String result) {
                            Logger.showInfo("result in callback",""+result);

                            try {

                                if (new JSONObject(result).getString("response").equalsIgnoreCase("TRUE")) {

                                    SharedPreferences.Editor editor = ApplicationClass.Loginsharedpreferences.edit();
                                    editor.putString("id", id);
                                    editor.commit();

                                    CustomAlert customAlert = new CustomAlert(VerificationLoginActivity.this, "Verification", new JSONObject(result).getString("message"), VerificationLoginActivity.this);
                                    customAlert.getListenerOKCancelFromNormalAlert();

                                } else {

                                    CustomAlert customAlert = new CustomAlert(VerificationLoginActivity.this, "Verification", new JSONObject(result).getString("message") + ".\nPlease try again.", VerificationLoginActivity.this);
                                    customAlert.createNormalAlert();
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



        linear_layout_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(VerificationLoginActivity.this,"Skip",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void callbackForAlert(String result) {

        Logger.showInfo("result",""+result);

        if (result.equals("ok")){

            Intent intent = new Intent(VerificationLoginActivity.this,BaseActivity.class);
            startActivity(intent);

            finish();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(VerificationLoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
