package com.dalaleen.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dalaleen.R;
import com.dalaleen.application.ApplicationClass;
import com.dalaleen.helper.ConstantClass;
import com.dalaleen.helper.CustomAlert;
import com.dalaleen.helper.CustomAlertForBackground;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.helper.MyCustomAlertBackgroundListener;
import com.dalaleen.helper.MyCustomAlertListener;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.logger.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MultipartBody;

/**
 * Created by su on 3/29/17.
 */

public class LoginActivity extends AppCompatActivity implements MyCustomAlertListener,MyCustomAlertBackgroundListener {

    EditText edit_phone_no,edit_password;
    ProgressDialog progDailog;
    String phone_number="";
    String password="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_phone_no= (EditText) findViewById(R.id.edit_phone_no);
        edit_password= (EditText) findViewById(R.id.edit_password);


        progDailog= new ProgressDialog(LoginActivity.this);
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        findViewById(R.id.tv_log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number=edit_phone_no.getText().toString().trim();
                password=edit_password.getText().toString().trim();

                findViewById(R.id.error_phone_no).setVisibility(View.GONE);
                findViewById(R.id.error_password).setVisibility(View.GONE);

                if(phone_number.equals("")){

                    edit_phone_no.requestFocus();
                    findViewById(R.id.error_phone_no).setVisibility(View.VISIBLE);

                }
                else {
                    findViewById(R.id.error_phone_no).setVisibility(View.GONE);
                    if (password.equals("")){

                        edit_password.requestFocus();
                        findViewById(R.id.error_password).setVisibility(View.VISIBLE);

                    }else {
                        findViewById(R.id.error_password).setVisibility(View.GONE);
                        if(new NetworkConnection(LoginActivity.this).netCheck()){
                            Toast.makeText(LoginActivity.this,"Submit",Toast.LENGTH_LONG).show();
                            loginDo();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Network Problem",Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });
        findViewById(R.id.linear_layout_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,ActivityDigitsVerification.class);
                startActivity(i);
            }
        });

        findViewById(R.id.linear_forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loginDo(){

        String  url=""+ConstantClass.BASE_URL+ ConstantClass.LOGIN;

        CustomAsynctask customAsynctask=new CustomAsynctask(LoginActivity.this,progDailog);

        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM);
        buildernew.addFormDataPart("phone", phone_number);
        buildernew.addFormDataPart("password", password);
        buildernew.addFormDataPart("lang", "en");

        customAsynctask.getResultListenerFromAsynctaskForPost(url, buildernew, new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (new JSONObject(result).getString("response").equalsIgnoreCase("TRUE")) {

                        CustomAlert customAlert = new CustomAlert(LoginActivity.this, "Login", "Log In successful.", LoginActivity.this);
                        customAlert.getListenerOKCancelFromNormalAlert();

                        JSONObject info_array = new JSONObject(result).getJSONObject("info");
                        Logger.showInfo("info", "" + info_array);

                        SharedPreferences.Editor editor = ApplicationClass.Loginsharedpreferences.edit();
                        editor.putString("user_id", info_array.getString("id"));
                        editor.putString("USERDETAILS",info_array+"");
                        editor.commit();


                    }else if(new JSONObject(result).getString("response").equalsIgnoreCase("DEACTIVE")){

                        final JSONObject info_array = new JSONObject(result).getJSONObject("info");

                        new AlertDialog.Builder(LoginActivity.this)
                                .setCancelable(false)
                                .setTitle("Activation")
                                .setMessage(new JSONObject(result).getString("message"))
                                .setPositiveButton("Activate now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {

                                            Intent intent = new Intent(LoginActivity.this, VerificationLoginActivity.class);
                                            intent.putExtra("id", info_array.getString("id"));
                                            startActivity(intent);
                                            dialogInterface.dismiss();

                                            finish();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create()
                                .show();
                    }
                    else if(new JSONObject(result).getString("response").equalsIgnoreCase("BLOCKED")){

                        Toast.makeText(LoginActivity.this,new JSONObject(result).getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else {

                        CustomAlert customAlert = new CustomAlert(LoginActivity.this, "Login Error", new JSONObject(result).getString("message") + ".\nPlease try again.", LoginActivity.this);
                        customAlert.createNormalAlert();

                        SharedPreferences.Editor editor = ApplicationClass.Loginsharedpreferences.edit();
                        editor.putString("user_id", "");
                        editor.commit();
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

    @Override
    public void callbackForAlert(String result) {

        Logger.showInfo("result",""+result);

        if (result.equals("ok")){

            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        CustomAlertForBackground customAlertForBackground=new CustomAlertForBackground(LoginActivity.this,"Exit","Do you want to exit application?",this);
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
