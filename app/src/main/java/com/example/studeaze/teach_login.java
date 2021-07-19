package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class teach_login extends AppCompatActivity {
    private EditText subcode, Tpassword;
    Button Tloginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_login);
        TextView textView = (TextView) findViewById(R.id.tlogin);
        Tloginbtn = findViewById(R.id.tlogin_btn);
        subcode = findViewById(R.id.subcode);
        Tpassword = findViewById(R.id.tpass);

        String s= "Teacher login";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 7, 0);// set color
        TextView tv= (TextView) findViewById(R.id.tlogin);
        tv.setText(ss1);

        Tloginbtn.setOnClickListener(v -> {
            if(subcode.getText().toString().equals("admin") && Tpassword.getText().toString().equals("admin123")){
                Toast.makeText(teach_login.this, "your now an admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(teach_login.this,admin.class);
                startActivity(intent);
            }else {
                LoginTeacher();

            }
        });
    }
    private void LoginTeacher()
    {
        String scode = subcode.getText().toString();
        String Tpass= Tpassword.getText().toString();


        if(TextUtils.isEmpty(scode))
        {
            Toast.makeText(teach_login.this, "Please Enter subject code....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(Tpass))
        {
            Toast.makeText(this, "Please Enter provided password....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AllowAccess(scode,Tpass);
        }
    }

    private void AllowAccess(final String scode, final String Tpass)
    {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("teachers").child(scode).exists())
                {
                    students userData = dataSnapshot.child("teachers").child(scode).getValue(students.class);

                    if (userData.getsubcode().equals(scode))
                    {

                        if (userData.getPassword().equals(Tpass)) {
                            Paper.book().write("subcode", scode);
                            Paper.book().write("password", Tpass);
                            Toast.makeText(teach_login.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(teach_login.this, teach_dash.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(teach_login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(teach_login.this, "Error in get method", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(teach_login.this, "Account with " + scode + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(teach_login.this, "Server Error", Toast.LENGTH_SHORT).show();

            }

        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(teach_login.this,home.class);
        startActivity(intent);
    }
}