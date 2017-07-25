package com.dalaleen.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * Created by bodhidipta on 03/04/17.
 */

public class CustomAlert {

    MyCustomAlertListener ml;
    MyCustomAlertBackgroundListener mlg;
    Context context;
    String title,message;


    // constructor
    public CustomAlert(Context context,String title,String message,MyCustomAlertListener ml){
        this.context=context;
        this.title=title;
        this.message=message;
        this.ml = ml;
    }

    public void createNormalAlert(){
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }



    public void getListenerOKCancelFromNormalAlert(){
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        /////////take listener event///////////////
                        ml.callbackForAlert("ok");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        /////////take listener event///////////////
                        ml.callbackForAlert("cancel");
                    }
                })
                .create()
                .show();
    }

}
