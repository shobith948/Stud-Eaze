package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class stud_dash extends AppCompatActivity implements View.OnClickListener{
    private TextView student_name;
    private CardView view_attendance, marks, notice, timetable;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_dash);

        student_name = findViewById(R.id.S_text_name);
        view_attendance = (CardView) findViewById(R.id.view_attendance);
        marks = (CardView) findViewById(R.id.S_marks);
        notice = (CardView) findViewById(R.id.S_notice);
        timetable = (CardView) findViewById(R.id.S_view_class);
        logout =  findViewById(R.id.S_log_out);

        view_attendance.setOnClickListener(this);
        marks.setOnClickListener(this);
        notice.setOnClickListener(this);
        timetable.setOnClickListener(this);

        Paper.init(this);

        String UserUsnKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");

        if(UserUsnKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserUsnKey) && !TextUtils.isEmpty(UserPasswordKey)){
                studentNameDisplay(UserUsnKey, UserPasswordKey);
            }
        }
        
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(stud_dash.this, home.class);
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
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.view_attendance:
                i = new Intent(this, ts_notice.class);
                startActivity(i);
                break;

            case R.id.S_marks:
                i = new Intent(this, home.class);
                startActivity(i);
                break;

            case R.id.S_notice:
                i = new Intent(this, ts_notice.class);
                startActivity(i);
                break;

            case R.id.S_view_class:
                i = new Intent(this, home.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(stud_dash.this,home.class);
        startActivity(intent);
    }
}