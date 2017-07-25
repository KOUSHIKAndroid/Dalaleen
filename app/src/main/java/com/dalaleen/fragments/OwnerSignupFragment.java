package com.dalaleen.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.dalaleen.Activities.LoginActivity;
import com.dalaleen.R;
import com.dalaleen.Activities.VerificationLoginActivity;
import com.dalaleen.custome_front.LatoRegular;
import com.dalaleen.helper.ConstantClass;
import com.dalaleen.helper.CustomAlert;
import com.dalaleen.Interface.CustomAsynctask;
import com.dalaleen.helper.MyCustomAlertListener;
import com.dalaleen.helper.NetworkConnection;
import com.dalaleen.logger.Logger;
import org.json.JSONObject;
import okhttp3.MultipartBody;

/**
 * Created by su on 3/29/17.
 */

public class OwnerSignupFragment extends Fragment implements MyCustomAlertListener {
    String phone_no,name,password,email;
    ProgressDialog progDailog;

    EditText edit_phone_no,edit_name,edit_email,edit_password,edit_confirm_password;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_signup, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_phone_no= (EditText) view.findViewById(R.id.edit_phone_no);
        edit_name= (EditText) view.findViewById(R.id.edit_name);
        edit_email= (EditText) view.findViewById(R.id.edit_email);
        edit_password= (EditText) view.findViewById(R.id.edit_password);
        edit_confirm_password= (EditText) view.findViewById(R.id.edit_confirm_password);


        edit_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")) {
                    if (isValidEmail(s)) {
                        view.findViewById(R.id.error_email).setVisibility(View.GONE);
                    } else {
                        view.findViewById(R.id.error_email).setVisibility(View.VISIBLE);
                    }
                }
                else {
                    view.findViewById(R.id.error_email).setVisibility(View.GONE);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        progDailog= new ProgressDialog(getActivity());
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        view.findViewById(R.id.tv_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone_no=edit_phone_no.getText().toString().trim();
                name=edit_name.getText().toString().trim();
                email=edit_email.getText().toString().trim();
                password=edit_password.getText().toString().trim();

                view.findViewById(R.id.error_phone_no).setVisibility(View.GONE);
                view.findViewById(R.id.error_name).setVisibility(View.GONE);
                view.findViewById(R.id.error_email).setVisibility(View.GONE);
                view.findViewById(R.id.error_password).setVisibility(View.GONE);
                view.findViewById(R.id.error_confirm_password).setVisibility(View.GONE);


                if (phone_no.equals("")){

                    edit_phone_no.requestFocus();
                    view.findViewById(R.id.error_phone_no).setVisibility(View.VISIBLE);

                }else {
                    view.findViewById(R.id.error_phone_no).setVisibility(View.GONE);
                    if (name.equals("")){

                        edit_name.requestFocus();
                        view.findViewById(R.id.error_name).setVisibility(View.VISIBLE);

                    }else {
                        view.findViewById(R.id.error_name).setVisibility(View.GONE);
                        if(email.equals("")){

                            edit_email.requestFocus();
                            view.findViewById(R.id.error_email).setVisibility(View.VISIBLE);

                        }else {
                            view.findViewById(R.id.error_email).setVisibility(View.GONE);

                            if(isValidEmail(email)){
                                view.findViewById(R.id.error_email).setVisibility(View.GONE);
                                if (password.equals("")){

                                    edit_password.requestFocus();
                                    view.findViewById(R.id.error_password).setVisibility(View.VISIBLE);

                                }else {
                                    view.findViewById(R.id.error_password).setVisibility(View.GONE);
                                    if (edit_confirm_password.getText().toString().trim().equals("")){

                                        edit_confirm_password.requestFocus();
                                        view.findViewById(R.id.error_confirm_password).setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        view.findViewById(R.id.error_confirm_password).setVisibility(View.GONE);
                                        if(!edit_confirm_password.getText().toString().trim().equals(password)){

                                            edit_confirm_password.requestFocus();
                                            view.findViewById(R.id.error_confirm_password).setVisibility(View.VISIBLE);
                                            ((TextView)view.findViewById(R.id.error_confirm_password)).setText("Confirm password Should be same as password");

                                        }else {
                                            view.findViewById(R.id.error_confirm_password).setVisibility(View.GONE);
                                            Toast.makeText(getActivity(),"Submit",Toast.LENGTH_LONG).show();

                                            if(new NetworkConnection(getActivity()).netCheck()){
                                                Toast.makeText(getActivity(),"Submit",Toast.LENGTH_LONG).show();
                                                SignUpOwner();
                                            }
                                            else {
                                                Toast.makeText(getActivity(),"Network Problem",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                view.findViewById(R.id.error_email).setVisibility(View.VISIBLE);
                                ((LatoRegular)view.findViewById(R.id.error_email)).setText( getActivity().getString(R.string.enter_valid_email_address));

                                edit_email.setFocusableInTouchMode(true);
                                edit_email.requestFocus();
                            }
                        }
                    }
                }
            }
        });


        view.findViewById(R.id.linear_layout_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),LoginActivity.class);
                getActivity().startActivity(i);
                getActivity().finish();
            }
        });

    }

    private void SignUpOwner(){

        String  url=""+ConstantClass.BASE_URL+ ConstantClass.Registration;

        CustomAsynctask customAsynctask=new CustomAsynctask(getActivity(),progDailog);

        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM);

        buildernew.addFormDataPart("name", name);
        buildernew.addFormDataPart("email", email);
        buildernew.addFormDataPart("password", password);
        buildernew.addFormDataPart("phone", phone_no);
        buildernew.addFormDataPart("user_type", "U");

        customAsynctask.getResultListenerFromAsynctaskForPost(url, buildernew, new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.showInfo("result in callback",""+result);

                try {

                    if (new JSONObject(result).getString("response").equalsIgnoreCase("TRUE")){

                        CustomAlert customAlert=new CustomAlert(getActivity(),"Sign Up","Sign Up successful.",OwnerSignupFragment.this);
                        customAlert.getListenerOKCancelFromNormalAlert();

                        JSONObject info_array=new JSONObject(result).getJSONObject("info");
                        Logger.showInfo("info",""+info_array);

                    }else{

                        CustomAlert customAlert=new CustomAlert(getActivity(),"Sign Up Error","Error Sign Up , "+new JSONObject(result).getString("message")+".\nPlease try again." ,OwnerSignupFragment.this);
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


    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void callbackForAlert(String result) {
        if (result.equals("ok")){

            Intent intent = new Intent(getActivity(), VerificationLoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }



}
