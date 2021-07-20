package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loading_dialog extends AppCompatActivity {

    private static Activity activity;
    private static AlertDialog dialog;

    public loading_dialog(){

    }

    public loading_dialog(Activity myActivity) {
        activity = myActivity;
    }

    static void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_loading_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    static void dismissDialog(){
        dialog.dismiss();
    }

}