package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialogAcitivity extends AppCompatActivity { //class for loading dialog

    private static Activity activity;
    private static AlertDialog dialog;

    public LoadingDialogAcitivity(){

    }
    //constructor
    public LoadingDialogAcitivity(Activity myActivity) {
        activity = myActivity;
    }
    //Function to start loading dialog
    static void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_loading_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
    //Function to dismiss loading dialog
    static void dismissDialog(){
        dialog.dismiss();
    }

}