package com.dalaleen.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.dalaleen.Activities.LoginActivity;
import com.dalaleen.R;
import com.dalaleen.Activities.VerificationLoginActivity;
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

public class AgentSignupFragment extends Fragment implements MyCustomAlertListener {
    EditText edit_phone_no,edit_name,edit_password;
    String phone_no,name,password;
    ProgressDialog progDailog;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agent_signup, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_phone_no= (EditText) view.findViewById(R.id.edit_phone_no);
        edit_name= (EditText) view.findViewById(R.id.edit_name);
        edit_password= (EditText) view.findViewById(R.id.edit_password);


        progDailog= new ProgressDialog(getActivity());
        progDailog.setMessage("Loading...");

        progDailog.setCancelable(false);
        progDailog.setCanceledOnTouchOutside(false);

        view.findViewById(R.id.tv_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone_no=edit_phone_no.getText().toString().trim();
                name=edit_name.getText().toString().trim();
                password=edit_password.getText().toString().trim();

                view.findViewById(R.id.error_phone_no).setVisibility(View.GONE);
                view.findViewById(R.id.error_name).setVisibility(View.GONE);
                view.findViewById(R.id.error_password).setVisibility(View.GONE);

                if (phone_no.equals("")){

                    edit_phone_no.requestFocus();
                    view.findViewById(R.id.error_phone_no).setVisibility(View.VISIBLE);

                }else {
                    view.findViewById(R.id.error_phone_no).setVisibility(View.GONE);
                    if(name.equals("")){

                        edit_name.requestFocus();
                        view.findViewById(R.id.error_name).setVisibility(View.VISIBLE);

                    }else {
                        view.findViewById(R.id.error_name).setVisibility(View.GONE);
                        if(password.equals("")){

                            edit_password.requestFocus();
                            view.findViewById(R.id.error_password).setVisibility(View.VISIBLE);

                        }else {
                            view.findViewById(R.id.error_password).setVisibility(View.GONE);

                            if(new NetworkConnection(getActivity()).netCheck()){
                                Toast.makeText(getActivity(),"Submit",Toast.LENGTH_LONG).show();

                                if(new NetworkConnection(getActivity()).netCheck()){
                                    Toast.makeText(getActivity(),"Submit",Toast.LENGTH_LONG).show();
                                    SignUpAgent();
                                }
                                else {
                                    Toast.makeText(getActivity(),"Network Problem",Toast.LENGTH_LONG).show();
                                }

                            }
                            {
                                Toast.makeText(getActivity(),"Network Problem",Toast.LENGTH_LONG).show();
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

    private void SignUpAgent(){

        String  url=""+ConstantClass.BASE_URL+ ConstantClass.Registration;

        CustomAsynctask customAsynctask=new CustomAsynctask(getActivity(),progDailog);

        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM);

        buildernew.addFormDataPart("name", name);
        buildernew.addFormDataPart("email", "");
        buildernew.addFormDataPart("password", password);
        buildernew.addFormDataPart("phone", phone_no);
        buildernew.addFormDataPart("user_type", "A");

        customAsynctask.getResultListenerFromAsynctaskForPost(url, buildernew, new CustomAsynctask.onAPIResponse() {
            @Override
            public void onSuccess(String result) {
                Logger.showInfo("result in callback",""+result);

                try {

                    if (new JSONObject(result).getString("response").equalsIgnoreCase("TRUE")){

                        CustomAlert customAlert=new CustomAlert(getActivity(),"Sign Up","Sign Up successful.",AgentSignupFragment.this);
                        customAlert.getListenerOKCancelFromNormalAlert();

                        JSONObject info_array=new JSONObject(result).getJSONObject("info");
                        Logger.showInfo("info",""+info_array);

                    }else{

                        CustomAlert customAlert=new CustomAlert(getActivity(),"Sign Up Error",new JSONObject(result).getString("message")+".\nPlease try again.",AgentSignupFragment.this);
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

    @Override
    public void callbackForAlert(String result) {

        if(result.equals("ok")) {

            Intent intent = new Intent(getActivity(), VerificationLoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


}
