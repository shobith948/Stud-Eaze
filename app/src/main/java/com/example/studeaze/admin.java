package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        TextView textView = (TextView) findViewById(R.id.admin_htext);

        String s= "Admin Dashboard";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,6, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 5, 0);// set color
        TextView tv= (TextView) findViewById(R.id.admin_htext);
        tv.setText(ss1);
    }
}