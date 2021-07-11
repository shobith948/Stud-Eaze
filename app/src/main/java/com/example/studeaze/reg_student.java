package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class reg_student extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_student);

        Spinner myspinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(reg_student.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Semester));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);

        TextView textView = (TextView) findViewById(R.id.reg_head);

        String s= "Student Register";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 7, 0);// set color
        TextView tv= (TextView) findViewById(R.id.reg_head);
        tv.setText(ss1);
    }
}