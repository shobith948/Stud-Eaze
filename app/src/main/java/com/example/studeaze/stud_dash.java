package com.example.studeaze;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class stud_dash extends AppCompatActivity {
    private TextView student_name;
    private Button view_attNmarks, timetable, logout , notice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_dash);

        student_name = findViewById(R.id.S_text_name);
        view_attNmarks = findViewById(R.id.view_MnA);
        notice = findViewById(R.id.S_notice);
        logout = findViewById(R.id.S_log_out);


        Paper.init(this);

        String UserUsnKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");

        if(UserUsnKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserUsnKey) && !TextUtils.isEmpty(UserPasswordKey)){
                studentNameDisplay(UserUsnKey, UserPasswordKey);
            }
        }

        view_attNmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stud_dash.this , marks_recycler.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(stud_dash.this, home.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stud_dash.this,ts_notice.class);
                startActivity(intent);

            }
        });

    }

    private void studentNameDisplay(String usn, String s_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("students").child(usn).exists()){
                    students studentsData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if(studentsData.getUsn().equals(usn)){
                        if(studentsData.getPassword().equals(s_pass)){
                            String s_name = studentsData.getName();
                            student_name.setText(s_name);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(stud_dash.this,home.class);
        startActivity(intent);
    }
}