package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class teach_dash extends AppCompatActivity {
    private TextView name;
    private Button take_attendance, timetable, notice, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_dash);

        name = findViewById(R.id.T_text_name);
        take_attendance = (Button) findViewById(R.id.take_attendance);
        timetable = (Button) findViewById(R.id.T_view_class);
        notice = (Button) findViewById(R.id.notice_display);
        logout = (Button) findViewById(R.id.T_log_out);

        Paper.init(this);

        String TeacherSubKey = Paper.book().read("subcode");
        String TeacherPasswordKey = Paper.book().read("t_password");

        if (TeacherSubKey != "" && TeacherPasswordKey != "")
        {
            if(!TextUtils.isEmpty(TeacherSubKey) && !TextUtils.isEmpty(TeacherPasswordKey))
            {
                teacherNameDisplay(TeacherSubKey, TeacherPasswordKey);
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(teach_dash.this, home.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teach_dash.this,ts_notice.class);
                startActivity(intent);

            }
        });
    }

    private void teacherNameDisplay(final String s_code, final String t_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("teachers").child(s_code).exists()) {
                    students teachersData = dataSnapshot.child("teachers").child(s_code).getValue(students.class);

                    if (teachersData.getsubcode().equals(s_code)) {
                        if (teachersData.getPassword().equals(t_pass)) {
                            String t_name = teachersData.getName();
                            name.setText(t_name);
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
        Intent intent = new Intent(teach_dash.this,home.class);
        startActivity(intent);
    }
}