package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class home extends AppCompatActivity {
    private Button student;
    private Button teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        student =(Button) findViewById(R.id.std_log);
        teacher =(Button) findViewById(R.id.teacher);
        Paper.init(this);

        String UserPhoneKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");

        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey, UserPasswordKey);

            }
        }

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


    private void AllowAccess(final String usn, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("").child(usn).exists()) {
                    Users userData = dataSnapshot.child("Users").child(usn).getValue(Users.class);

                    if (userData.getUsn().equals(usn)) {
                        if (userData.getPassword().equals(password)) {

                            Intent intent = new Intent(home.this, stud_dash.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(home.this, "Retry Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(home.this, "Account with " + usn + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
