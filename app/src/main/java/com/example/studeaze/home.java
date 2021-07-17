package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        String UserUsnKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");
        String TeacherSubKey = Paper.book().read("subcode");
        String TeacherPasswordKey = Paper.book().read("t_password");

        if (UserUsnKey != "" && UserPasswordKey != "")
        {
            if(!TextUtils.isEmpty(UserUsnKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserUsnKey, UserPasswordKey);

            }
        }
        if (TeacherSubKey != "" && TeacherPasswordKey != "")
        {
            if(!TextUtils.isEmpty(TeacherSubKey) && !TextUtils.isEmpty(TeacherPasswordKey))
            {
                if(TeacherSubKey.equals("admin") && TeacherPasswordKey.equals("admin123")){
                    AdminAllowAccess();
                }
                else {
                    TeacherAllowAccess(TeacherSubKey, TeacherPasswordKey);
                }

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
                if (dataSnapshot.child("students").child(usn).exists()) {
                    students studentsData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if (studentsData.getUsn().equals(usn)) {
                        if (studentsData.getPassword().equals(password)) {

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

    private void AdminAllowAccess() {
        Intent intent = new Intent(home.this, admin.class);
        startActivity(intent);
    }

    private void TeacherAllowAccess(final String s_code, final String T_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("teachers").child(s_code).exists()) {
                    students teachersData = dataSnapshot.child("teachers").child(s_code).getValue(students.class);

                    if (teachersData.getsubcode().equals(s_code)) {
                        if (teachersData.getPassword().equals(T_pass)) {

                            Intent intent = new Intent(home.this, teach_dash.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(home.this, "Retry Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(home.this, "Account with " + s_code + " does not exist", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
