package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {
    private Button student;
    private Button teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        student =(Button) findViewById(R.id.std_log);
        teacher =(Button) findViewById(R.id.teacher);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teach_login();
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    public  void teach_login(){
        Intent intent = new Intent(this,teach_login.class);
        startActivity(intent);
    }
    public  void login(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }
}