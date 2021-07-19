package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.paperdb.Paper;

public class admin extends AppCompatActivity {
    Button rm_std, add_teach, rm_teach, add_notice, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        TextView textView = (TextView) findViewById(R.id.admin_htext);
        rm_std = findViewById(R.id.remove_std);
        add_teach = findViewById(R.id.add_teacher);
        rm_teach = findViewById(R.id.remove_teacher);
        add_notice = findViewById(R.id.notice);
        logout = findViewById(R.id.log_out);


        String s= "Admin Dashboard";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,6, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 5, 0);// set color
        TextView tv= (TextView) findViewById(R.id.admin_htext);
        tv.setText(ss1);

        rm_std.setOnClickListener(View ->{
            remStudent();
        });

        add_teach.setOnClickListener(View ->{
            regTeacher();
        });

        rm_teach.setOnClickListener(View ->{
            remTeacher();
        });

        add_notice.setOnClickListener(View ->{
            add_Notice();
        });

        Paper.init(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(admin.this, home.class);
                startActivity(intent);
            }
        });
    }
    public  void regTeacher(){
        Intent intent = new Intent(this,add_teacher.class);
        startActivity(intent);
    }
    public  void remStudent(){
        Intent intent = new Intent(this,add_teacher.class);
        startActivity(intent);
    }
    public  void remTeacher(){
        Intent intent = new Intent(this,add_teacher.class);
        startActivity(intent);
    }
    public  void add_Notice(){
        Intent intent = new Intent(this,add_notice.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(admin.this,home.class);
        startActivity(intent);
    }
}