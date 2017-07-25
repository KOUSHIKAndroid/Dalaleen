package com.dalaleen.Interface;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dalaleen.logger.Logger;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by su on 4/8/17.
 */

public class CustomAsynctask {

        public interface onAPIResponse{
            void  onSuccess(String result);
            void  onError(String Error);
        }




    Context context;
    onAPIResponse onAPIResponse;
    String url;
    ProgressDialog progressDialog;

    public CustomAsynctask(Context context,ProgressDialog progressDialog){
        this.context=context;
        this.progressDialog=progressDialog;
    }



    public void getResultListenerFromAsynctaskForGet(String url1,onAPIResponse ml){
        this.onAPIResponse=ml;
        this.url=url1;

        new AsyncTask<Void, Void, Void>() {

            private String respose = null;
            private Exception exception=null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Logger.showInfo("url", "" + url);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (!isCancelled()) {

                        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(5000, TimeUnit.MILLISECONDS).build();
                        Request request = new Request.Builder().url(url).build();
                        Response response = client.newCall(request).execute();

                        respose = response.body().string();

                        Logger.showInfo("response", "respose_::" + respose);
                        Logger.showInfo("response", "respose_ww_message::" + response.message());
                        Logger.showInfo("response", "respose_ww_headers::" + response.headers());
                        Logger.showInfo("response", "respose_ww_isRedirect::" + response.isRedirect());
//                        Logger.showInfo("response", "respose_ww_body::" + response.body().string());
                    }
                } catch (Exception e) {
                    this.exception=e;
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!isCancelled() && exception==null) {
                    onAPIResponse.onSuccess(respose);

                    }else {
                    onAPIResponse.onSuccess(exception.getMessage()+"");
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public void getResultListenerFromAsynctaskForPost(String Url, final MultipartBody.Builder buildernew, final onAPIResponse onAPIResponse){
        this.onAPIResponse=onAPIResponse;
        this.url=Url;

        new AsyncTask<Void,Void,Void>(){

            private String respose = null;
            private Exception exception=null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Logger.showInfo("url",""+url);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    if (!isCancelled()){

                        MultipartBody requestBody = buildernew.build();

                        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(5000, TimeUnit.MILLISECONDS).build();

                        Request request = new Request.Builder()
                                .url(url)
                                .method("POST", RequestBody.create(null, new byte[0]))
                                .post(requestBody)
                                .build();

                        Response response = client.newCall(request).execute();
                        respose= response.body().string();
                        Logger.showInfo("respose",respose);

                    }
                }catch (Exception e){
                    this.exception=e;

                    Logger.showInfo("Exception",""+e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!isCancelled() && exception==null){
                            onAPIResponse.onSuccess(respose);
                    }else {
                    onAPIResponse.onError(exception.getMessage());

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
