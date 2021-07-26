package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class teach_dash extends AppCompatActivity implements View.OnClickListener{
    private TextView name;
    private CardView take_attendance, marks, notice, timetable;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_dash);

        name = findViewById(R.id.T_text_name);
        take_attendance = (CardView) findViewById(R.id.take_attendance);
        marks = (CardView) findViewById(R.id.T_marks);
        notice = (CardView) findViewById(R.id.notice_display);
        timetable = (CardView) findViewById(R.id.T_view_class);
        logout = (Button) findViewById(R.id.T_log_out);

        take_attendance.setOnClickListener(this);
        marks.setOnClickListener(this);
        notice.setOnClickListener(this);
        timetable.setOnClickListener(this);

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

//        notice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(teach_dash.this, ts_notice.class);
//                startActivity(intent);
//            }
//        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(teach_dash.this, home.class);
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
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.take_attendance:
                i = new Intent(this, ts_notice.class);
                startActivity(i);
                break;

            case R.id.T_marks:
                i = new Intent(this, home.class);
                startActivity(i);
                break;

            case R.id.T_notice:
                i = new Intent(this, ts_notice.class);
                startActivity(i);
                break;

            case R.id.T_view_class:
                i = new Intent(this, home.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(teach_dash.this,home.class);
        startActivity(intent);
    }
}