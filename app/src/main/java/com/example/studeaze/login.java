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

public class login extends AppCompatActivity {

    TextView reg;
    private EditText userusn , userpassword;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        setContentView(R.layout.activity_stud_login);
        TextView textView = (TextView) findViewById(R.id.textView7);
        loginbtn = findViewById(R.id.s_login);
        userusn = findViewById(R.id.usn);
        userpassword = findViewById(R.id.pass);


        String s= "Student login";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.home_btn), 0, 7, 0);// set color
        TextView tv= (TextView) findViewById(R.id.textView7);
        tv.setText(ss1);

        reg = findViewById(R.id.reg);


        loginbtn.setOnClickListener(v -> {
            if(userusn.getText().toString().equals("admin") && userpassword.getText().toString().equals("admin123")){
                Intent intent = new Intent(login.this,admin.class);
           startActivity(intent);
            }else {
                LoginUser();

            }
        });

        reg.setOnClickListener(v -> {
            regUser();
        });
    }

    public  void regUser(){
        Intent intent = new Intent(this,reg_student.class);
        startActivity(intent);
    }
    private void LoginUser()
    {
        String usn = userusn.getText().toString();
        String password= userpassword.getText().toString();


        if(TextUtils.isEmpty(usn))
        {
            Toast.makeText(login.this, "Please Enter your usn number....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter your password....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.getTrimmedLength(usn) < 9)
        {
            Toast.makeText(login.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AllowAccess(usn, password);
        }
    }

    private void AllowAccess(final String usn, final String password)
    {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("students").child(usn).exists())
                {
                    students userData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if (userData.getUsn().equals(usn))
                    {

                        if (userData.getPassword().equals(password)) {
                            Paper.book().write("usn", usn);
                            Paper.book().write("password", password);
                            Toast.makeText(login.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, stud_dash.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(login.this, "Error in get method", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(login.this, "Account with " + usn + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Server Error", Toast.LENGTH_SHORT).show();

            }

        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(login.this,home.class);
        startActivity(intent);
    }
}
